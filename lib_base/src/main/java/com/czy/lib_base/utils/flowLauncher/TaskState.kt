package com.czy.lib_base.utils.flowLauncher

import androidx.annotation.IntDef

/**
 * @author yangqing
 * @time 2021/4/26 10:20
 * @describe
 */
@IntDef(
    TaskState.IDLE,
    TaskState.START,
    TaskState.RUNNING,
    TaskState.FINISHED
)
annotation class TaskState {
    companion object{
        const val IDLE = 0//精静止
        const val START = 1//启动，可能需要等待调度
        const val RUNNING = 2//运行
        const val FINISHED = 3//运行结束
    }
}