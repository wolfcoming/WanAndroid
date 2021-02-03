package com.example.lib_imageloader.image.glide;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.czy.lib_base.utils.file.FileUtils;
import com.example.lib_imageloader.image.BaseImageLoaderStrategy;
import com.example.lib_imageloader.image.ProgressLoadListener;
import com.example.lib_imageloader.image.listener.ImageSaveListener;
import com.example.lib_imageloader.image.listener.SourceReadyListener;

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
        loadNormal(imageView.getContext(), url, placeholder, imageView);
    }

    @Override
    public void loadImage(Context context, String url, int placeholder, ImageView imageView) {
        loadNormal(context, url, placeholder, imageView);
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
    public void loadImageWithProgress(String url, final ImageView imageView, final ProgressLoadListener listener) {
//        Glide.with(imageView.getContext()).using(new ProgressModelLoader(new ProgressUIListener() {
//            @Override
//            public void update(final int bytesRead, final int contentLength) {
//                imageView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        listener.update(bytesRead, contentLength);
//                    }
//                });
//            }
//        })).load(url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).
//                listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        listener.onException();
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        listener.onResourceReady();
//                        return false;
//                    }
//                }).into(imageView);
    }


    @Override
    public void loadGifWithPrepareCall(String url, ImageView imageView, final SourceReadyListener listener) {
//        Glide.with(imageView.getContext()).load(url).asGif()
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE).
//                listener(new RequestListener<String, GifDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        listener.onResourceReady(resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
//                        return false;
//                    }
//                }).into(imageView);
    }

    @Override
    public void loadImageWithPrepareCall(String url, ImageView imageView, int placeholder, final SourceReadyListener listener) {
//        Glide.with(imageView.getContext()).load(url)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(placeholder)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        listener.onResourceReady(resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
//                        return false;
//                    }
//                }).into(imageView);
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
            String size = FileUtils.getSize(Glide.getPhotoCacheDir(context.getApplicationContext()));
            return size;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void saveImage(Context context, String url, String savePath, String saveFileName, ImageSaveListener listener) {

    }

    /**
     * load image with Glide
     */
    private void loadNormal(final Context ctx, final String url, int placeholder, ImageView imageView) {
        Glide.with(ctx).load(url)
                .placeholder(placeholder)
                .into(imageView);
    }

    /**
     * load image with Glide
     */
    private void loadGif(final Context ctx, String url, int placeholder, ImageView imageView) {

    }

}