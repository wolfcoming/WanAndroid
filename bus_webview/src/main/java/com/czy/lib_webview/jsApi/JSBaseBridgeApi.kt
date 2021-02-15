package com.czy.lib_webview.jsApi

import com.czy.lib_webview.jsBridge.CallbackRes
import com.czy.lib_webview.jsBridge.InvokeParams
import com.czy.lib_webview.jsBridge.JSBridgeApi
import com.tencent.smtt.sdk.WebView
import org.json.JSONObject
import java.lang.Exception

abstract class JSBaseBridgeApi : JSBridgeApi() {
    lateinit var paramJSON: JSONObject
    override fun handle(web: WebView, param: InvokeParams, callback: CallbackRes) {
        try {
            paramJSON = JSONObject(param.param)
        } catch (e: Exception) {
            paramJSON = JSONObject("{}")
            callBackForFunc(callback.getBackFailParam("json 转化异常"), web)
            return
        }

    }

}