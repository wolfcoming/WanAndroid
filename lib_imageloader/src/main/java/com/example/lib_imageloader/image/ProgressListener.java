package com.example.lib_imageloader.image;

interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
