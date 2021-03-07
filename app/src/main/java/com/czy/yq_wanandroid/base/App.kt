package com.czy.yq_wanandroid.base

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.czy.business_base.dataSave.DataSaveProxy
import com.czy.lib_base.utils.ContentWrapperUtils
import com.czy.yq_wanandroid.launchstarter.TaskDispatcher
import com.czy.yq_wanandroid.tasks.*


class App : Application() {
    companion object {
        lateinit var mContext: Context
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
            .addTask(InitCompontService())
            .addTask(InitLogTask())
            .start()
        taskDispatcher.await()

        DataSaveProxy.getInstance().init(this,"common_data_save")
        initDarkMode()

    }

    private fun initDarkMode() {
        val boolean = DataSaveProxy.getInstance().getBoolean("darkMode", false)
        if (boolean) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


}