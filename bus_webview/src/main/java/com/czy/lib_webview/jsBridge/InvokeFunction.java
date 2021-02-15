package com.czy.lib_webview.jsBridge;

import android.webkit.JavascriptInterface;

import com.tencent.smtt.sdk.WebView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvokeFunction {
    private WebView mWebView;
    private HashMap<String, JSBridgeApi> functionMap = new HashMap<>();
    private List<Object> oldJSBridges = new ArrayList<Object>();


    public InvokeFunction(WebView webView) {
        mWebView = webView;
    }


    public void addJSApi(JSBridgeApi jsApi) {
        functionMap.put(jsApi.function(), jsApi);
    }

    public HashMap<String, JSBridgeApi> getFunctionMap() {
        return functionMap;
    }

    public void addFunctionMap(HashMap<String, JSBridgeApi> map) {
        functionMap.putAll(map);
    }

    /**
     * @param
     * @return
     * @description 所有函数执行入口
     * @date: 2019/11/8 10:07
     * @author: BINZ
     */

    @JavascriptInterface
    public void invoke(String method, String params, String callback) {
        JSCurrentFunction.getInstance().setCurrent(method,params,callback);
        invokeFunction(method, params, callback);
    }

    public void invokePermissionCallback(String method, String params, String callback) {
        invokeFunction(method, params, callback);
    }

    /**
     * @description 调用桥接方法
     * @date: 2019/11/8 10:10
     * @author: BINZ
     */
    private void invokeFunction(String method, String params, String callback) {
        InvokeParams invokeParams = new InvokeParams();
        invokeParams.setMethod(method);
        invokeParams.setParam(params);
        CallbackRes callbackRes = new CallbackRes();
        callbackRes.callbackId = callback;

        //查找新继承JSApi桥接
        JSBridgeApi api = functionMap.get(method);
        if (null == api) {
            //TODO 提示报错
            return;
        }
        api.handle(mWebView, invokeParams, callbackRes);
    }

    private boolean getMethod(Object obj, String name, String param, CallbackRes callbackRes) {
        try {
            Method invokeMethod = obj.getClass().getMethod(name, new Class[]{Class.forName("java.lang.String"), CallbackRes.class});
            invokeMethod.invoke(obj, new Object[]{param, callbackRes});
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }
}