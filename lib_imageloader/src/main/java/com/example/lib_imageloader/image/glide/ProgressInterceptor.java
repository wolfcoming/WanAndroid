package com.example.lib_imageloader.image.glide;

import com.example.lib_imageloader.image.listener.ProgressListener;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ProgressInterceptor implements Interceptor {

    public static final Map<String, WeakReference<ProgressListener>> LISTENER_MAP = new HashMap<>();

    public static void addListener(String url, WeakReference<ProgressListener> listener) {
        LISTENER_MAP.put(url, listener);
    }


    public static void removeListener(String url) {
        LISTENER_MAP.remove(url);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        ResponseBody body = response.body();
        Response newResponse =
                response.newBuilder().body(new ProgressResponseBody(url, body)).build();
        return newResponse;
    }
}
