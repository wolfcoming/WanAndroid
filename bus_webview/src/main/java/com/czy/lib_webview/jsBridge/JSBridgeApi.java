package com.czy.lib_webview.jsBridge;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebView;

public abstract class JSBridgeApi {
    private final String TAG = "JSAPIBROS";
    public abstract String function();
    public abstract void handle(WebView web, InvokeParams param, CallbackRes callback);

    /**
     * @description 回调函数封装 走js桥中的onCallback方法
     */
    public void callBackForFunc(CallbackRes callBackParams, final WebView webview) {
        final String res = new Gson().toJson(callBackParams);
        ((Activity) webview.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl("javascript:onCallback(" + res + ")");
            }
        });
    }

    /**
     * 直接调用方法 js方法（该方法已经在js桥文件中挂载到了window）
     * @param callBackParams
     * @param callBack
     * @param webview
     */
    public void callBackForFunc(CallbackRes callBackParams, String callBack, WebView webview) {
        String res = new Gson().toJson(callBackParams);
        ((Activity) webview.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String function = "var functionJSAPI = " + callBack;
                function = "javascript:" + function + ";" + "functionJSAPI(" + res + ");";
                Log.d(TAG, function);
                webview.loadUrl(function);
            }
        });

    }
}
