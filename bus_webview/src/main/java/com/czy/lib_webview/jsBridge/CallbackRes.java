package com.czy.lib_webview.jsBridge;

import com.google.gson.Gson;

public class CallbackRes {
    public static final String SUCCESS_CODE = "1";
    public static final String FAILED_CODE = "0";

    public String code = SUCCESS_CODE;
    public String msg = "ok";
    public Object result = "";
    public String callbackId;

    public String getCallBackStr(CallbackRes callBackRes) {
        return new Gson().toJson(callBackRes);
    }

//    public void callBack(CallbackRes callBackParams, String callBack, WebView webview) {
//        String res = new Gson().toJson(callBackParams);
//        String function = "var functionJSAPI = " + callBack;
//        function = "javascript:" + function + ";" + "functionJSAPI(" + res + ");";
//        webview.loadUrl(function);
//    }

    @Override
    public String toString() {
        return "CallbackRes{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + String.valueOf(result) +
                ", callbackId='" + callbackId + '\'' +
                '}';
    }


    public CallbackRes getBackFailParam() {
        return getBackFailParam("failed");
    }

    public CallbackRes getBackFailParam(String msg) {
        return this.getBackFailParam(msg, "");
    }

    public CallbackRes getBackFailParam(String msg, Object result) {
        this.code = FAILED_CODE;
        this.msg = msg;
        this.result = result;
        return this;
    }

    public CallbackRes getBackSuccessParam() {
        return getBackSuccessParam("success");
    }
    public CallbackRes getBackSuccessParam(Object result) {
        return getBackSuccessParam("success",result);
    }
    public CallbackRes getBackSuccessParam(String msg) {
        return getBackFailParam(msg, "");
    }

    public CallbackRes getBackSuccessParam(String msg, Object result) {
        this.code = SUCCESS_CODE;
        this.msg = msg;
        this.result = result;
        return this;
    }

}
