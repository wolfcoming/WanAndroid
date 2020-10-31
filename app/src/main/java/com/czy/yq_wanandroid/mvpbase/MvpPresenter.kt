package com.czy.yq_wanandroid.mvpbase

@Suppress("UNCHECKED_CAST")
public abstract class MvpPresenter<V : IView>: IPresenter {
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


}