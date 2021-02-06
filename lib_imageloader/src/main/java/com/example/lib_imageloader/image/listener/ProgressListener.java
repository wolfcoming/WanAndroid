package com.example.lib_imageloader.image.listener;

public interface ProgressListener {

    /**
     * 图片加载进度回调
     *
     * @param isDone
     * @param progress
     */
    void onLoadProgress(boolean isDone, int progress);

    /**
     * 加载失败
     */
    void onLoadFailed();
}

