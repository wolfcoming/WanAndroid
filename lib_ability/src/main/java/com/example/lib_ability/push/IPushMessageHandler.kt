package com.example.lib_ability.push

import android.content.Context
import org.json.JSONObject

/**
 * @author yangqing
 * @time 2021/5/19 11:12
 * @describe
 */
interface IPushMessageHandler {
    //处理 通知栏点击行为
    fun dealWithCustomAction(context: Context?, message: JSONObject?){

    }
    //自己解析，自定义消息格式
    fun dealWithCustomMessage(p0: Context?, jsonObject: JSONObject){

    }
    //自定义notifycation样式
    fun dealWithNotificationMessage(p0: Context?, jsonObject: JSONObject){

    }

    fun onFailure(s: String, s1: String) {

    }

    fun onSuccess(deviceToken: String) {

    }
}