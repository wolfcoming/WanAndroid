package com.czy.lib_base.utils

import com.czy.lib_base.ext.log


class LauncherTime {

    companion object{
        private var sTime:Long = 0L

        fun startRecrod(){
            this.sTime = System.currentTimeMillis()
        }

        fun endRecord(){
            val costTime = System.currentTimeMillis() - this.sTime
            costTime.log("LauncherTime")
        }

    }

}