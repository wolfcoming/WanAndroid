package com.czy.business_base.tasks

import com.czy.business_base.CrashHandler
import com.czy.business_base.launchstarter.task.Task
import com.tencent.bugly.crashreport.CrashReport

class InitBuglyTask :Task() {
    override fun run() {
        //bugly
        CrashReport.initCrashReport(mContext, "e049243189", true);
        CrashHandler.getCrashHander().init(mContext)
    }
}