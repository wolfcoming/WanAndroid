package com.czy.lib_base.utils.flowLauncher

import androidx.core.os.TraceCompat
import java.lang.RuntimeException
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

/**
 *                    .::::.
 *                  .::::::::.
 *                 :::::::::::
 *             ..:::::::::::'
 *           '::::::::::::'
 *             .::::::::::
 *        '::::::::::::::..
 *             ..::::::::::::.
 *           ``::::::::::::::::
 *            ::::``:::::::::'        .:::.
 *           ::::'   ':::::'       .::::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.:::::::::'      ':::::.
 *      .::'         ::::::::::::::'         ``::::.
 *  ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 *                    '.:::::'                    ':'````..
 *@time 2021/4/26 10:10
 *@author yangqing
 *@describe
 */
abstract class Task @JvmOverloads constructor(
    val id: String,
    val isAsyncTask: Boolean,
    val delayMills: Long = 0,//延迟执行的时间
    val priority: Int = 0
) : Runnable, Comparable<Task> {

    var executeTime: Long = 0//任务执行时间
    var state: Int = TaskState.IDLE //任务状态
        protected set
    val dependTasks: MutableList<Task> = ArrayList()//前置任务
    val behindTasks: MutableList<Task> = ArrayList()//后置任务
    private val taskListeners: MutableList<TaskListener> = ArrayList()
    private var taskRuntimeListener: TaskRuntimeListener? = TaskRuntimeListener()
    val dependTasksName: MutableList<String> = ArrayList()
    fun addTaskListener(taskListener: TaskListener) {
        if (!taskListeners.contains(taskListener)) {
            this.taskListeners.add(taskListener)
        }
    }

    open fun start() {
        if (state != TaskState.IDLE) {
            throw RuntimeException("cann't run task $id again")
        }
        toStart()
        executeTime = System.currentTimeMillis()
        //执行当前任务
        executeTask(this)
    }

    val taskCompartor = Comparator<Task> { o1, o2 -> Utils.compareTask(o1, o2) }
    private fun executeTask(task: Task) {

    }

    private fun dependTaskFinished(dependTask: Task) {
        //如果没有前置任务，则不需要管
        if (dependTasks.isEmpty()) {
            return
        }
        dependTasks.remove(dependTask)
        //前置任务均执行完毕后，开始执行该任务
        if (dependTasks.isEmpty()) {
            start()
        }
    }

    //给当前task添加一个前置的依赖任务
    open fun dependOn(task: Task) {
        if (task != this) {
            dependTasks.add(task)
            dependTasksName.add(task.id)
            // 依赖任务，也就是当前任务的前置任务，也需要添加进来
            if(!task.behindTasks.contains(this)){
                task.behindTasks.add(this)
            }
        }
    }



    override fun run() {
        //改变任务状态
        TraceCompat.beginSection(id)
        toRunning()
        run(id)
        toFinish()
        //通知后置任务去执行
        notifyBehindTask()
        recycle()
        TraceCompat.endSection()

    }

    private fun notifyBehindTask() {
        if (behindTasks.isNotEmpty()) {
            if (behindTasks.size > 1) {
                Collections.sort(behindTasks, taskCompartor)
            }
            //遍历behindTask后置任务，通知他们 ，前置完成
            for (behindTask in behindTasks) {
                //告知后置任务，你的其中一个前置任务已经会自行完毕
                behindTask.dependTaskFinished(this)
            }
        }
    }

    private fun recycle() {
        dependTasks.clear()
        behindTasks.clear()
        taskListeners.clear()
        taskRuntimeListener = null
    }

    private fun toFinish() {
        state = TaskState.FINISHED
        for (taskListener in taskListeners) {
            taskListener.onFinished(this)
        }
        taskRuntimeListener?.onFinished(this)
    }

    private fun toRunning() {
        state = TaskState.RUNNING
        for (taskListener in taskListeners) {
            taskListener.onRunning(this)
        }
        taskRuntimeListener?.onRunning(this)
    }

    private fun toStart() {
        state = TaskState.START
        for (taskListener in taskListeners) {
            taskListener.onStart(this)
        }
        taskRuntimeListener?.onStart(this)
    }

    abstract fun run(id: String)

    override fun compareTo(other: Task): Int {
        return Utils.compareTask(this, other)
    }
}