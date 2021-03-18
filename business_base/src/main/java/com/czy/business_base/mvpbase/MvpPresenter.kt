package com.czy.business_base.mvpbase

import com.czy.business_base.api.Transformer
import io.reactivex.rxjava3.core.Observable

@Suppress("UNCHECKED_CAST")
public abstract class MvpPresenter<V : IView> : IPresenter {
    var baseView: V? = null

    override fun attach(view: IView) {
        this.baseView = view as V
    }


    override fun detach() {
        this.baseView = null
    }

    fun isAttach(): Boolean {
        return this.baseView != null
    }

    fun showLoading() {
        this.baseView?.showLoading()
    }

    fun hideLoading() {
        this.baseView?.hideLoading()
    }

    fun <T> commonNetObservableDeal(observable: Observable<T>): Observable<T> {
        return observable.compose(Transformer.threadSwitchAndBindLifeCycle(baseView))
            .compose(Transformer.serverCodeDeal())//网络结果统一处理
    }


}