package com.czy.yq_wanandroid.mvpbase

import android.content.Context

@Suppress("UNCHECKED_CAST")
public abstract class MvpPresenter<V : IView>: IPresenter {
    protected var context: Context? = null
    var baseView: V? = null

    override fun attach(view: IView) {
        this.baseView = view as V
        this.context = view.getContent()
    }


    override fun detach() {
        this.baseView = null
        this.context = null
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