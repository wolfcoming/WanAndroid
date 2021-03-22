package com.czy.business_base.net;

import com.czy.business_base.Constants;
import com.czy.business_base.net.entity.BaseResult;
import com.czy.lib_net.ApiException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;

public abstract class ApiSubscriberHelper<T> extends DisposableObserver<T> {
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(@NonNull T t) {
        if (t instanceof BaseResult) {
            BaseResult<?> baseResult = (BaseResult<?>) t;
            //未成功
            if (baseResult.getErrorCode() != Constants.API_SUCCESS_CODE) {
                SpecialCodeDeal.INSTANCE.dealSpecialCode(baseResult);
                ApiException apiException = new ApiException(baseResult.getErrorCode(), baseResult.getErrorMsg());
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
