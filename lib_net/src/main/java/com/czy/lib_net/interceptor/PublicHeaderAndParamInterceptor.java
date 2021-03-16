package com.czy.lib_net.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class PublicHeaderAndParamInterceptor implements Interceptor {
    HashMap<String, String> headers;
    HashMap<String, Object> params;

    public PublicHeaderAndParamInterceptor(HashMap<String, String> headers, HashMap<String, Object> params) {
        this.headers = headers;
        this.params = params;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();

        //添加公共请求头
        if(headers!=null){
            Iterator<String> iterator = headers.keySet().iterator();
            if (iterator.hasNext()) {
                String key = iterator.next();
                String value = headers.get(key);
                builder.addHeader(key, value);
            }
        }

        //添加公共请求参数
        if(params!=null){
            HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
            Iterator paramIterator = params.entrySet().iterator();
            while (paramIterator.hasNext()) {
                Map.Entry entry = (Map.Entry) paramIterator.next();
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
            builder.url(httpUrlBuilder.build());
        }


        return chain.proceed(builder.build());
    }
}
