package com.czy.business_base.mvpbase

interface IPresenter {
    fun attach(view: IView)
    fun detach()
}