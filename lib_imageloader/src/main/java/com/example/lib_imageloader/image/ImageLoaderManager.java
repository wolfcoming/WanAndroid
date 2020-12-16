package com.example.lib_imageloader.image;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.lib_imageloader.R;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

/**
 * 图处加载类，外界唯一调用类,直持为view,notifaication,appwidget加载图片
 */
public class ImageLoaderManager {

    private ImageLoaderManager() {
    }

    public static ImageLoaderManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static ImageLoaderManager instance = new ImageLoaderManager();
    }


    /**
     * 不带回调的加载
     */
    public void displayImageForView(ImageView imageView, String url) {
        this.displayImageForView(imageView, url, null);
    }

    /**
     * 带回调的加载图片方法
     */
    public void displayImageForView(ImageView imageView, String url,
                                    CustomRequestListener requestListener) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(url)
                .apply(initCommonRequestOption())
                .addListener(requestListener)
                .transition(withCrossFade())
                .into(imageView);
    }


    public void displayImageForCircle(final ImageView imageView, String url) {
        displayImageWidthRadius(imageView, url, -1);
    }

    public void displayImageWidthRadius(final ImageView imageView, String url, float radius) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(url)
                .apply(initCommonRequestOption())
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(final Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(imageView.getResources(), resource);
                        if (radius == -1) {
                            circularBitmapDrawable.setCircular(true);
                        } else {
                            circularBitmapDrawable.setCornerRadius(radius);
                        }
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    @SuppressLint("StaticFieldLeak")
    public void displayImageForViewGroup(final ViewGroup group, String url) {
        Glide.with(group.getContext())
                .asBitmap()
                .load(url)
                .apply(initCommonRequestOption())
                .into(new SimpleTarget<Bitmap>() {//设置宽高
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                                                @Nullable Transition<? super Bitmap> transition) {
                        final Bitmap res = resource;
                        new AsyncTask<Void, Void, Drawable>() {
                            @Override
                            protected Drawable doInBackground(Void... voids) {
                                Drawable drawable =
                                        new BitmapDrawable(
                                                group.getContext().getResources(),
                                                Utils.doBlur(res, 100, true)
                                        );
                                return drawable;
                            }

                            @Override
                            protected void onPostExecute(Drawable drawable) {
                                super.onPostExecute(drawable);
                                group.setBackground(drawable);
                            }
                        }.execute();

                    }
                });
    }


    /**
     * 为非view加载图片
     */
    private void displayImageForTarget(Context context, Target<Bitmap> target, String url) {
        this.displayImageForTarget(context, target, url, null);
    }

    /**
     * 为非view加载图片
     */
    private void displayImageForTarget(Context context, Target<Bitmap> target, String url,
                                       CustomRequestListener requestListener) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(initCommonRequestOption())
                .transition(withCrossFade())
                .fitCenter()
                .listener(requestListener)
                .into(target);
    }

    /*
     * 初始化Notification Target
     */
    private NotificationTarget initNotificationTarget(Context context, int id, RemoteViews rv,
                                                      Notification notification, int NOTIFICATION_ID) {
        NotificationTarget notificationTarget =
                new NotificationTarget(context, id, rv, notification, NOTIFICATION_ID);
        return notificationTarget;
    }

    private RequestOptions initCommonRequestOption() {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_error)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(false)
                .priority(Priority.NORMAL);
        return options;
    }
}
