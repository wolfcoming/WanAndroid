package com.czy.business_base.tasks

import android.app.Application
import android.util.Log
import com.czy.business_base.launchstarter.task.Task
import com.czy.lib_log.HiLog
import com.example.lib_ability.push.IPushMessageHandler
import com.example.lib_ability.push.PushInitialization
import com.igexin.sdk.IUserLoggerInterface
import com.igexin.sdk.PushManager


class InitPushTask : Task() {
    override fun run() {
        //极光推送
//        PushManager.getInstance().initialize(mContext)
//        PushManager.getInstance()
//            .setDebugLogger(mContext, IUserLoggerInterface { s -> Log.i("PUSH_LOG", s) })
        PushInitialization.init(mContext as Application, "uMeng", object : IPushMessageHandler {
            override fun onSuccess(deviceToken: String) {
                super.onSuccess(deviceToken)
                HiLog.e("友盟 推送注册成功")
            }

            override fun onFailure(s: String, s1: String) {
                super.onFailure(s, s1)
                HiLog.e("友盟 推送注册失败$s")
            }
        })
    }

}