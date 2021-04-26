package com.czy.lib_base.utils.flowLauncher

/**
 * @author yangqing
 * @time 2021/4/26 10:26
 * @describe
 */
interface TaskListener {
    fun onStart(task: Task)
    fun onRunning(task: Task)
    fun onFinished(task: Task)
}