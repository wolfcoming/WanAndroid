package com.czy.lib_webview.jsBridge;

import java.util.HashMap;
import java.util.Map;

public class JSCurrentFunction {

    private static JSCurrentFunction currentFunction;
    private String method, params, callback;

    private JSCurrentFunction() {

    }

    public static JSCurrentFunction getInstance() {
        if (currentFunction == null) {
            currentFunction = new JSCurrentFunction();
        }

        return currentFunction;
    }

    public void setCurrent(String method, String params, String callback) {
        currentFunction.method = method;
        currentFunction.params = params;
        currentFunction.callback = callback;
    }

    public Map<CurrentParamResult, String> getCurrent() {
        Map<CurrentParamResult, String> resultMap = new HashMap<>();
        resultMap.put(CurrentParamResult.METHOD, currentFunction.method);
        resultMap.put(CurrentParamResult.PARAMS, currentFunction.params);
        resultMap.put(CurrentParamResult.CALLBACK, currentFunction.callback);
        return resultMap;
    }

    public enum CurrentParamResult {
        METHOD,
        PARAMS,
        CALLBACK
    }
}
