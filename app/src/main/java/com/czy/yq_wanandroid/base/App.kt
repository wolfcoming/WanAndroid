package com.czy.yq_wanandroid.base

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.czy.business_base.sp.SpHelpUtils
import com.czy.lib_base.utils.ContentWrapperUtils
import com.czy.lib_base.utils.LauncherTime
import com.czy.lib_log.HiLogConfig
import com.czy.lib_log.HiLogManager
import com.czy.yq_wanandroid.launchstarter.TaskDispatcher
import com.czy.yq_wanandroid.tasks.InitArouter
import com.czy.yq_wanandroid.tasks.InitBuglyTask
import com.czy.yq_wanandroid.tasks.InitCompontService
import com.czy.yq_wanandroid.tasks.InitSmartRefresh


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
        initDarkMode()
        HiLogManager.init(object :HiLogConfig(){

        })
        TaskDispatcher.init(this)
        val taskDispatcher = TaskDispatcher.createInstance()
        taskDispatcher
            .addTask(InitBuglyTask())
            .addTask(InitArouter())
            .addTask(InitSmartRefresh())
            .addTask(InitCompontService())
            .start()
        taskDispatcher.await()

    }

    private fun initDarkMode() {
        val boolean = SpHelpUtils.getBoolean("darkMode", false)
        if (boolean) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


}