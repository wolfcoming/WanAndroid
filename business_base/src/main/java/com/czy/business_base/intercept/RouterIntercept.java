package com.czy.business_base.intercept;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.czy.business_base.ArouterConfig;
import com.czy.business_base.service.ServiceFactory;
import com.czy.lib_log.HiLog;

@Interceptor(name = "路由拦截器", priority = 8)
public class RouterIntercept implements IInterceptor {
    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        int extra = postcard.getExtra();
        //登录拦截
        if ((extra & ArouterConfig.intercept_login) != 0) {
            if (!ServiceFactory.Companion.getUserService().isLogin()) {
                callback.onInterrupt(new Throwable("需要登录"));
                HiLog.e("需要登录");
                ARouter.getInstance()
                        .build(ArouterConfig.login)
                        .withString("targetPath", postcard.getPath())
                        .navigation();
            } else {
                callback.onContinue(postcard);
            }
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
