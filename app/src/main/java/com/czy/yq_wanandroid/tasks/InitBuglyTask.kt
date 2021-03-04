package com.czy.yq_wanandroid.tasks

import com.czy.yq_wanandroid.base.CrashHandler
import com.czy.yq_wanandroid.launchstarter.task.Task
import com.tencent.bugly.crashreport.CrashReport

class InitBuglyTask :Task() {
    override fun run() {
        //bugly
        CrashReport.initCrashReport(mContext, "e049243189", true);
        CrashHandler.getCrashHander().init(mContext)
    }
}