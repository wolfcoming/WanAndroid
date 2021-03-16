package com.czy.business_base.net.interceptor;

import com.czy.lib_base.utils.ContentWrapperUtils;
import com.czy.lib_base.utils.NetUtils;
import com.czy.lib_log.HiLog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络缓存器 -- 注意添加到网络拦截器
 */
public class NetCacheInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (NetUtils.isConnected(ContentWrapperUtils.mContext)) {
            //获取头部信息
            String cacheControl = request.cacheControl().toString();
            HiLog.et("NetCacheInterceptor",cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", cacheControl)
                    .build();
        }
        return response;
    }
}
