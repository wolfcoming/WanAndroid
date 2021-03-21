package com.czy.business_base.tasks

import android.util.Log
import com.czy.business_base.launchstarter.task.Task
import com.igexin.sdk.IUserLoggerInterface
import com.igexin.sdk.PushManager


class InitPushTask : Task() {
    override fun run() {
        PushManager.getInstance().initialize(mContext)
        PushManager.getInstance()
            .setDebugLogger(mContext, IUserLoggerInterface { s -> Log.i("PUSH_LOG", s) })
    }

}