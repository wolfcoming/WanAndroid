package com.example.lib_ability.push

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.umeng.message.UmengMessageHandler
import com.umeng.message.UmengNotificationClickHandler
import com.umeng.message.entity.UMessage
import org.json.JSONObject

/**
 * @author yangqing
 * @time 2021/5/19 10:17
 * @describe
 */
object PushInitialization {
    val TAG = "push"
    fun init(application: Application, channel: String, pusManHandler: IPushMessageHandler) {
        initUmentPush(application, channel, pusManHandler)
        initOEMPushSdk(application)
    }

    private fun initOEMPushSdk(application: Application) {

    }

    private fun initUmentPush(
        application: Application,
        channel: String,
        pusManHandler: IPushMessageHandler?
    ) {
        UMConfigure.init(
            application, "60a388e209f7064aed41f7c9",
            channel,
            UMConfigure.DEVICE_TYPE_PHONE,
            "fae337fd83a3d6883250eb9b5ce8302f"
        )
        //获取消息推送实例
        //获取消息推送实例
        val pushAgent = PushAgent.getInstance(application)
        //注册推送服务 每次调用register都会回调该接口

        //注册推送服务 每次调用register都会回调该接口
        pushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                Log.i(TAG, "device token: $deviceToken")
                pusManHandler?.onSuccess(deviceToken)
            }

            override fun onFailure(s: String, s1: String) {
                Log.i(TAG, "register failed: $s $s1")
                pusManHandler?.onFailure(s, s1)
            }
        })

//        pushAgent.messageHandler = object : UmengMessageHandler() {
//            override fun dealWithNotificationMessage(p0: Context?, p1: UMessage?) {
//                if (pusManHandler != null) {
//                    //TODO 自己解析数据
//                    pusManHandler.dealWithNotificationMessage(p0, JSONObject())
//                    Log.i(TAG, "dealWithNotificationMessage")
//                } else {
//                    super.dealWithNotificationMessage(p0, p1)
//                }
//            }
//
//            override fun dealWithCustomMessage(p0: Context?, p1: UMessage?) {
//                if (pusManHandler != null) {
//                    //TODO 自己解析数据
//                    pusManHandler.dealWithCustomMessage(p0, JSONObject())
//                    Log.i(TAG, "dealWithNotificationMessage")
//                } else {
//                    super.dealWithCustomMessage(p0, p1)
//                }
//            }
//        }
//
//        pushAgent.notificationClickHandler = object : UmengNotificationClickHandler() {
//            override fun dealWithCustomAction(p0: Context?, p1: UMessage?) {
//                if (pusManHandler != null) {
//                    //TODO 自己解析数据
//                    pusManHandler.dealWithCustomAction(p0, JSONObject())
//                    Log.i(TAG, "dealWithNotificationMessage")
//                } else {
//                    super.dealWithCustomAction(p0, p1)
//                }
//            }
//        }

        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                super.onActivityPreCreated(activity, savedInstanceState)
                PushAgent.getInstance(application).onAppStart();
                Log.i(TAG, "onAppStart")
            }
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

        })
    }
}