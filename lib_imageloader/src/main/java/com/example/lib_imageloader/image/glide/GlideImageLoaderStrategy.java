package com.example.lib_imageloader.image.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.czy.lib_base.utils.file.FileUtils;
import com.example.lib_imageloader.image.BaseImageLoaderStrategy;
import com.example.lib_imageloader.image.listener.ProgressListener;
import com.example.lib_imageloader.image.listener.SourceReadyListener;

import java.lang.ref.WeakReference;

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url)
                .placeholder(imageView.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }


    @Override
    public void loadImageWithAppCxt(String url, ImageView imageView) {
        Glide.with(imageView.getContext().getApplicationContext()).load(url)
                .placeholder(imageView.getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
    }

    @Override
    public void loadImage(String url, int placeholder, ImageView imageView) {
        loadNormal(imageView.getContext(), url, placeholder, imageView, 0);
    }

    @Override
    public void loadImage(String url, int placeholder, ImageView imageView, float radius) {
        loadNormal(imageView.getContext(), url, placeholder, imageView, radius);
    }

    @Override
    public void loadImage(Context context, String url, int placeholder, ImageView imageView) {
        loadNormal(context, url, placeholder, imageView, 0);
    }


    @Override
    public void loadCircleImage(String url, int placeholder, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).placeholder(placeholder).dontAnimate()
                .transform(new CircleCrop())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView);
    }

    @Override
    public void loadCircleBorderImage(String url, int placeholder, ImageView imageView, float borderWidth, int borderColor) {
        Glide.with(imageView.getContext()).load(url).placeholder(placeholder).dontAnimate()
                .transform(new CircleBorderTransformation(borderWidth,borderColor))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView);
    }



    @Override
    public void loadGifImage(String url, int placeholder, ImageView imageView) {
        loadGif(imageView.getContext(), url, placeholder, imageView);
    }

    @Override
    public void loadImageWithProgress(String url, final ImageView imageView, final ProgressListener listener) {
        ProgressInterceptor.addListener(url, new WeakReference<ProgressListener>(listener));
        GlideApp.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(new DrawableImageViewTarget(imageView){
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
//                        imageView.setImageResource(R.color.color_646464);
                    }


                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        ProgressListener progressListener = ProgressInterceptor.LISTENER_MAP.get(url).get();
                        if(progressListener!=null){
                            progressListener.onLoadFailed();
                        }
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<
                                                ? super Drawable> transition) {
                        super.onResourceReady(resource, transition);
                        ProgressInterceptor.removeListener(url);

                    }
                });
    }

    @Override
    public void loadImageWithPrepareCall(String url, ImageView imageView, int placeholder, final SourceReadyListener listener) {
        Glide.with(imageView.getContext()).load(url)
                .placeholder(placeholder)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        listener.onResourceReady(resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
                        return false;
                    }

                })
                .into(imageView);
    }

    @Override
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context.getApplicationContext()).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void trimMemory(Context context, int level) {
        Glide.get(context).trimMemory(level);
    }

    @Override
    public String getCacheSize(Context context) {
        try {
            return FileUtils.getSize(Glide.getPhotoCacheDir(context.getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * load image with Glide
     */
    private void loadNormal(final Context ctx, final String url, int placeholder, ImageView imageView, float radius) {
        if(radius>0){
            Glide.with(ctx).load(url)
                    .placeholder(placeholder)
                    .transform(new RoundedCorners((int) radius))
                    .into(imageView);
        }else {
            Glide.with(ctx).load(url)
                    .placeholder(placeholder)
                    .into(imageView);
        }
    }

    /**
     * load image with Glide
     */
    private void loadGif(final Context ctx, String url, int placeholder, ImageView imageView) {
        Glide.with(ctx)
                .asGif()
                .load(url)
                .placeholder(placeholder)
                .into(imageView);
    }

}