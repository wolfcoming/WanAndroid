package com.czy.lib_webview.js

import com.tencent.smtt.sdk.WebView

open class BaseJsInteration {
    var mWebview:WebView?=null

    constructor(mWebview: WebView?) {
        this.mWebview = mWebview
    }
}