package com.czy.business_base.service

interface AppService {
    //记录阅读历史
    fun recodeReadHistory(url: String, title: String, time: Long)
}