package com.czy.business_base.tasks

import com.czy.business_base.launchstarter.task.Task
import com.czy.lib_log.HiLogConfig
import com.czy.lib_log.HiLogConfig.JsonParser
import com.czy.lib_log.HiLogManager
import com.czy.lib_log.printer.HiConsolePrinter
import com.google.gson.Gson

class InitLogTask: Task() {
    override fun run() {
        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }

            override fun includeThread(): Boolean {
                return false
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        },
            HiConsolePrinter()
//            HiFilePrinter.getInstance(mContext.filesDir.path+File.separator+"log",1000*60*60*24*7)
        )
    }

//    override fun needWait(): Boolean {
//        return true
//    }
}