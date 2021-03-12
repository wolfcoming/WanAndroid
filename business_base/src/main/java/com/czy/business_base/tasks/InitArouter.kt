package com.czy.business_base.tasks

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.BuildConfig
import com.czy.business_base.launchstarter.task.Task

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