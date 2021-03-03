package com.czy.business_base.api;


import com.czy.business_base.Constants;
import com.czy.business_base.mvpbase.IView;
import com.czy.business_base.net.ApiErrorHandlerUtil;
import com.czy.business_base.net.entity.BaseResult;
import com.czy.business_base.service.ServiceFactory;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Transformer {


    /**
     * 线程切换
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> threadSwitch() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 线程切换+生命周期绑定
     *
     * @param view
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> threadSwitchAndBindLifeCycle(IView view) {
        return upstream -> upstream
                .compose(threadSwitch())
                .compose(view.bindLifecycleEvent());
    }

    public static <T> ObservableTransformer<T, T> serverCodeDeal() {
        return serverCodeDeal(true);
    }

    /**
     * 错误码统一处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> serverCodeDeal(boolean jumpLogin) {
        return upstream -> upstream.map(t -> {
            if (t instanceof BaseResult) {// 还可以新增else 处理其他数据结构的错误
                if (Constants.API_NEED_LOGIN == ((BaseResult) t).getErrorCode()) {
                    if (jumpLogin) {
                        ServiceFactory.Companion.getUserService().gotoLogin();
                    }
                }
                ApiErrorHandlerUtil.INSTANCE.throwApiException(t);
            }
            return t;
        }).onErrorResumeNext((Function<Throwable, ObservableSource<? extends T>>) throwable -> {
            return Observable.error(ApiErrorHandlerUtil.INSTANCE.getWanApiException(throwable));
        });
    }
}
