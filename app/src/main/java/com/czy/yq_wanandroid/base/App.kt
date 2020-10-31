package com.czy.yq_wanandroid.base

import android.app.Application
import android.os.StrictMode


class App : Application() {
    override fun onCreate() {
        super.onCreate()
//        enabledStrictMode()
    }

    private fun enabledStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .penaltyDeath() //
                .build()
        )
    }
}