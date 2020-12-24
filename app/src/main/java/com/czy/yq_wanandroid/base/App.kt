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
import com.czy.yq_wanandroid.launchstarter.TaskDispatcher
import com.czy.yq_wanandroid.launchstarter.task.Task
import com.czy.yq_wanandroid.tasks.InitArouter
import com.czy.yq_wanandroid.tasks.InitBuglyTask
import com.czy.yq_wanandroid.tasks.InitSmartRefresh
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

        TaskDispatcher.init(this)
        val taskDispatcher = TaskDispatcher.createInstance()
        taskDispatcher
            .addTask(InitBuglyTask())
            .addTask(InitArouter())
            .addTask(InitSmartRefresh())
            .start()
        taskDispatcher.await()

    }


}