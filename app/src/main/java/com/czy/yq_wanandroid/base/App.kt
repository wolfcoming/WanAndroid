package com.czy.yq_wanandroid.base

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.StrictMode
import com.czy.yq_wanandroid.utils.ContentWrapperUtils


class App : Application() {
    companion object {
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        ContentWrapperUtils.init(this)
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