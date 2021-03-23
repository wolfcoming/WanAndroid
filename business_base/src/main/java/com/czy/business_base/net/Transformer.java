package com.czy.business_base.net;


import com.czy.business_base.Constants;
import com.czy.business_base.mvpbase.IView;
import com.czy.business_base.net.ApiErrorHandlerUtil;
import com.czy.business_base.net.entity.BaseResult;
import com.czy.business_base.service.ServiceFactory;
import com.czy.lib_net.ApiException;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;
import com.trello.rxlifecycle4.components.RxFragment;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle4.components.support.RxFragmentActivity;

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

    public static <T> ObservableTransformer<T, T> threadSwitchAndBindLifeCycle(RxAppCompatActivity appCompatActivity) {
        return upstream -> upstream
                .compose(threadSwitch())
                .compose(appCompatActivity.bindUntilEvent(ActivityEvent.DESTROY));
    }

    public static <T> ObservableTransformer<T, T> threadSwitchAndBindLifeCycle(RxFragment rxFragment) {
        return upstream -> upstream
                .compose(threadSwitch())
                .compose(rxFragment.bindUntilEvent(FragmentEvent.DESTROY));
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
            if (t instanceof BaseResult) {
                BaseResult<?> baseResult = (BaseResult<?>) t;
                if (baseResult.getErrorCode() != Constants.API_SUCCESS_CODE) {
                    SpecialCodeDeal.INSTANCE.dealSpecialCode(baseResult);
                    throw new ApiException(baseResult.getErrorCode(), baseResult.getErrorMsg());
                }
            }
            return t;
        }).onErrorResumeNext((Function<Throwable, ObservableSource<? extends T>>) throwable -> {
            return Observable.error(ApiErrorHandlerUtil.INSTANCE.getWanApiException(throwable));
        });
    }
}
