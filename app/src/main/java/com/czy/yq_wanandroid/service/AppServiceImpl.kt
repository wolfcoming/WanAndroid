package com.czy.yq_wanandroid.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.business_base.ArouterConfig
import com.czy.business_base.Constants.READHISTORY_MAX_COUNT
import com.czy.business_base.service.AppService
import com.czy.yq_wanandroid.room.AppDatabase
import com.czy.yq_wanandroid.room.entity.ReadHistory
import com.infoholdcity.basearchitecture.self_extends.log

@Route(path = ArouterConfig.appService)
class AppServiceImpl : AppService {
    override fun recodeReadHistory(url: String, title: String, time: Long) {
        Thread {
            AppDatabase.instance.readHistoryDao()
                .addReadHistory(ReadHistory(url, title, System.currentTimeMillis()))
            AppDatabase.instance.readHistoryDao().autoDeleteOverMax(READHISTORY_MAX_COUNT)
        }.start()
    }

    override fun init(context: Context?) {
        "AppService 实例化".log()
    }
}