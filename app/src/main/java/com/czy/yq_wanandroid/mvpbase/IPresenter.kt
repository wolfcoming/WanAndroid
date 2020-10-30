package com.czy.yq_wanandroid.mvpbase

interface IPresenter {
    fun attach(view: IView)
    fun detach()
}