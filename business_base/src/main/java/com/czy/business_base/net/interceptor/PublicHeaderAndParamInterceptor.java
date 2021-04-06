package com.czy.business_base.net.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublicHeaderAndParamInterceptor implements Interceptor {
    public static HashMap<String, String> headers = new HashMap<>();
    public static HashMap<String, String> params = new HashMap<>();

    public PublicHeaderAndParamInterceptor() {
        headers.clear();
        params.clear();

        headers.put("headerKey1", "headerValue1");
        headers.put("headerKey2", "headerValue2");
        headers.put("headerKey3", "headerValue3");

        params.put("paramKey1", "paramValue1");
        params.put("paramKey2", "paramValue2");
        params.put("paramKey3", "paramValue3");
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();

        //添加公共请求头
        if (headers != null) {
            for (String key : headers.keySet()) {
                String value = headers.get(key);
                builder.addHeader(key, value);
            }
        }

        //添加公共请求参数
        if (params != null) {
            if (request.method().equals("GET")) {
                //重新构建url
                HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
                for (String key : params.keySet()) {
                    String value = params.get(key);
                    httpUrlBuilder.addEncodedQueryParameter(key, value);
                }
                builder.url(httpUrlBuilder.build());
            } else {
                //创建新的请求表单
                FormBody.Builder newFormBodyBuilder = new FormBody.Builder();
                if(request.body() instanceof FormBody){
                    FormBody formBody = (FormBody) request.body();
                    //迁移旧的数据
                    for (int i = 0; i < formBody.size(); i++) {
                        newFormBodyBuilder.add(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                }
                //追加新的数据
                for (String key : params.keySet()) {
                    String value = params.get(key);
                    newFormBodyBuilder.add(key, value);
                }
                builder.post(newFormBodyBuilder.build()).build();//构造新的请求体
            }
        }
        return chain.proceed(builder.build());
    }
}
