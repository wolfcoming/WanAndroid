package com.czy.yq_wanandroid.mvpbase

import android.content.Context

interface IView {
    fun getContent():Context
    fun showLoading()
    fun hideLoading()
}