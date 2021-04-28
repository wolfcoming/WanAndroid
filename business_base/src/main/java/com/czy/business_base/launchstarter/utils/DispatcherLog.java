package com.czy.business_base.launchstarter.utils;

import android.util.Log;

import com.czy.business_base.BuildConfig;


public class DispatcherLog {

    private static boolean sDebug = BuildConfig.DEBUG;

    public static void i(String msg) {
        if (!sDebug) {
            return;
        }
        Log.e("SSSSSS", "i: " + msg);
    }

    public static boolean isDebug() {
        return sDebug;
    }

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

}
