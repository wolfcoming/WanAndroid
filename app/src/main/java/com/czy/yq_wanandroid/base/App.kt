package com.czy.yq_wanandroid.base

import com.czy.business_base.BaseApplication


class App : BaseApplication() {

    override fun initMouduleApplication() {
        //获取所有继承自 BaseApplication的类

        Thread.sleep(1000)
    }
}