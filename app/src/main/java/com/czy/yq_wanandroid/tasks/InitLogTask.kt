package com.czy.yq_wanandroid.tasks

import com.czy.lib_log.HiLogConfig
import com.czy.lib_log.HiLogManager
import com.czy.lib_log.printer.HiConsolePrinter
import com.czy.lib_log.printer.HiFilePrinter
import com.czy.yq_wanandroid.launchstarter.task.Task
import com.google.gson.Gson
import java.io.File

class InitLogTask:Task() {
    override fun run() {
        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }

            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        },
            HiConsolePrinter(),
            HiFilePrinter.getInstance(mContext.filesDir.path+File.separator+"log",1000*60*60*24*7)
        )
    }

    override fun needWait(): Boolean {
        return true
    }
}