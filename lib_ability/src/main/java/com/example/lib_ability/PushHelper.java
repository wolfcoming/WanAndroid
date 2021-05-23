package com.example.lib_ability;

import android.content.Context;
import android.util.Log;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

public class PushHelper {
    static String TAG =  "PushHelper";
    public static void init(Context context) {

         //获取消息推送实例
        PushAgent pushAgent = PushAgent.getInstance(context);
        //注册推送服务，每次调用register方法都会回调该接口
        pushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：--> " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "注册失败：--> " + "s:" + s + ",s1:" + s1);
            }
        });

    }
}