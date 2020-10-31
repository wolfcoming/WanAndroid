package com.czy.yq_wanandroid.mvpbase

import com.trello.rxlifecycle4.LifecycleTransformer

interface IView {
    fun showLoading()
    fun hideLoading()

    //处理 网络请求 界面关闭时 rxjava自动注销
    fun <T> bindLifecycleEvent(): LifecycleTransformer<T>
}