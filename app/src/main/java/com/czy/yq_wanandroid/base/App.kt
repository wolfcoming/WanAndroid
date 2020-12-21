package com.czy.yq_wanandroid.base

import android.app.Application
import android.content.Context
import android.os.Debug
import android.os.StrictMode
import androidx.core.os.TraceCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.lib_base.utils.ContentWrapperUtils
import com.czy.lib_base.utils.LauncherTime
import com.czy.yq_wanandroid.BuildConfig
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport


class App : Application() {
    companion object {
        lateinit var mContext: Context
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        LauncherTime.startRecrod()
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        ContentWrapperUtils.init(this)
        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "e049243189", true);
        initArouter()

    }

    private fun initArouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.printStackTrace();
        }
        ARouter.init(this)
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

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(context)
        }

    }


}