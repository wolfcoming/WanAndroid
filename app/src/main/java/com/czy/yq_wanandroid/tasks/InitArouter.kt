package com.czy.yq_wanandroid.tasks

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.yq_wanandroid.BuildConfig
import com.czy.yq_wanandroid.launchstarter.task.Task

class InitArouter : Task() {
    override fun needWait(): Boolean {
        return true
    }


    override fun run() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.printStackTrace();
        }
        ARouter.init(mContext as Application)
    }
}