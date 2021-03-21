package com.czy.business_base.net;

import com.czy.business_base.Constants;
import com.czy.business_base.net.entity.BaseResult;
import com.czy.business_base.service.ServiceFactory;
import com.czy.lib_net.ApiException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;

public abstract class ApiSubscriberHelper<T> extends DisposableObserver<T> {
    @Override
    public void onNext(@NonNull T t) {
        if (t instanceof BaseResult) {
            //未成功
            if (((BaseResult) t).getErrorCode() != Constants.API_SUCCESS_CODE) {
                if (Constants.API_NEED_LOGIN == ((BaseResult) t).getErrorCode()) {
                    ServiceFactory.Companion.getUserService().gotoLogin();
                }
                ApiException apiException = new ApiException(((BaseResult) t).getErrorCode(), ((BaseResult) t).getErrorMsg());
                onFailed(apiException);
            } else {
                onResult(t);
            }
        } else {
            onResult(t);
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(@NonNull Throwable e) {
        ApiException apiException = ApiErrorHandlerUtil.INSTANCE.getWanApiException(e);
        onFailed(apiException);
    }

    protected abstract void onResult(@NonNull T t);

    protected abstract void onFailed(@NonNull ApiException msg);
}
