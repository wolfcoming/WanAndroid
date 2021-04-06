package com.czy.business_base.service

import com.alibaba.android.arouter.facade.template.IProvider

interface AppService :IProvider{
    //记录阅读历史
    fun recodeReadHistory(url: String, title: String, time: Long)
}