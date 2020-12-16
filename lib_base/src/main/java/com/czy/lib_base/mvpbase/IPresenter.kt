package com.czy.lib_base.mvpbase

interface IPresenter {
    fun attach(view: IView)
    fun detach()
}