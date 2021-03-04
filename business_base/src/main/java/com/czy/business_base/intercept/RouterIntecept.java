package com.czy.business_base.intercept;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.czy.business_base.ArouterConfig;

@Interceptor(name = "路由拦截器", priority = 9)
public class RouterIntecept implements IInterceptor {
    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        int extra = postcard.getExtra();
        if ((extra & ArouterConfig.intercept_login) != 0) {
            callback.onInterrupt(new Throwable("需要登录"));
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, "需要登录", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
