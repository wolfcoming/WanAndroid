package com.czy.lib_base.utils.flowLauncher

/**
 * @author yangqing
 * @time 2021/4/26 10:54
 * @describe
 */

object Utils {
    fun compareTask(task1: Task, task2: Task): Int {
        if(task1.priority<task2.priority){
            return 1
        }
        if(task1.priority>task2.priority){
            return -1
        }
        return 0
    }
}