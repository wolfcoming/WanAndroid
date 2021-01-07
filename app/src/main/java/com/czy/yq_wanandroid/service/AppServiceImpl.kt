package com.czy.yq_wanandroid.service

import com.czy.business_base.service.AppService
import com.czy.lib_base.Constants.READHISTORY_MAX_COUNT
import com.czy.yq_wanandroid.room.AppDatabase
import com.czy.yq_wanandroid.room.entity.ReadHistory

class AppServiceImpl : AppService {
    override fun recodeReadHistory(url: String, title: String, time: Long) {
        Thread {
            AppDatabase.instance.readHistoryDao()
                .addReadHistory(ReadHistory(url, title, System.currentTimeMillis()))
            AppDatabase.instance.readHistoryDao().autoDeleteOverMax(READHISTORY_MAX_COUNT)
        }.start()
    }
}