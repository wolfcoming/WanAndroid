package com.czy.lib_net.interceptor;

import com.czy.lib_base.utils.ContentWrapperUtils;
import com.czy.lib_base.utils.NetUtils;
import com.czy.lib_log.HiLog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 离线缓存-无网的情况下走缓存（ 注意添加到普通拦截器）
 */
public class OfflineCacheInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetUtils.isConnected(ContentWrapperUtils.mContext)) {
            HiLog.et("OfflineCacheInterceptor", "没有网络了，开始走缓存数据");
            request = request.newBuilder()
                    .cacheControl(new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(60, TimeUnit.SECONDS)
                            .build())
//                    .header("Cache-Control", "public, only-if-cached, max-stale=60")
                    .build();
        }
        return chain.proceed(request);
    }
}
