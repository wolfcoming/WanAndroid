package com.czy.business_base.mvpbase;

import com.czy.business_base.api.Transformer;

import io.reactivex.rxjava3.core.Observable;

public class BaseModel {

    public <T> Observable<T> commonDealNetObservableResult(Observable<T> observable) {
        return observable.compose(Transformer.serverCodeDeal());
    }

}
