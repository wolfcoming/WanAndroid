package com.czy.lib_webview.jsApi;

import com.czy.lib_base.utils.NetUtils;
import com.czy.lib_base.utils.PhoneInfoUtils;
import com.czy.lib_webview.jsBridge.CallbackRes;
import com.czy.lib_webview.jsBridge.InvokeParams;
import com.tencent.smtt.sdk.WebView;


public class JSGetVersion extends JSBaseBridgeApi {

    @Override
    public String function() {
        return "getVersion";
    }

    @Override
    public void handle(WebView web, InvokeParams parm, CallbackRes callback) {
        super.handle(web,parm,callback);
        Object res = "";
        switch (paramJSON.optString("info")) {
            case "build":
                res = String.valueOf(PhoneInfoUtils.getVersionCode(web.getContext()));
                break;
            case "version":
                //客户端版本
                res = PhoneInfoUtils.packageName(web.getContext());
                break;
            case "ip":
                res = NetUtils.getIp();
                break;
            case "mac":
                res = PhoneInfoUtils.getMAC(web.getContext());
                break;
            case "os":
                res = "android-" + android.os.Build.VERSION.RELEASE;
                break;
            case "model":
                //手机型号
                res = PhoneInfoUtils.getModelName();
                break;
            case "ID":
                res = PhoneInfoUtils.getAndoidId(web.getContext());
                break;
            case "brand":
                res = PhoneInfoUtils.getBoard();
                break;
            case "produce":
                res = PhoneInfoUtils.getProductName();
                break;
        }
        callBackForFunc(callback.getBackSuccessParam(res), web);
    }
}
