package com.czy.lib_base.utils.flowLauncher

import android.util.Log
import com.czy.lib_base.BuildConfig

/**
 * @author yangqing
 * @time 2021/4/26 10:31
 * @describe
 */
class TaskRuntimeListener:TaskListener {
    override fun onStart(task: Task) {
        if(BuildConfig.DEBUG){
            Log.e(TAG, "---onStart---" )
        }
    }

    override fun onRunning(task: Task) {
        if(BuildConfig.DEBUG){
            Log.e(TAG, "---onRunning---" )
        }
    }

    override fun onFinished(task: Task) {

    }

    companion object{
        const val TAG:String = "TaskFlow"
    }
}