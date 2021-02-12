package com.czy.lib_webview.js

import android.webkit.JavascriptInterface
import com.czy.lib_base.ext.log
import com.tencent.smtt.sdk.WebView

open class JsInteration(mWebview: WebView?) : BaseJsInteration(mWebview) {
    @JavascriptInterface
    fun calledByJs(param1: String, func:Any): String {
        param1.log()
        func.toString().log()
        Thread.sleep(2000)
        return param1+"sdhit"
    }
}