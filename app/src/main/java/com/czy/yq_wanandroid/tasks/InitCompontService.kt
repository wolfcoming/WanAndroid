package com.czy.yq_wanandroid.tasks

import com.czy.business_base.service.BaseServiceInit
import com.czy.yq_wanandroid.launchstarter.task.Task
import java.lang.Exception

/**
 * 初始化个组件服务
 */
class InitCompontService : Task() {
    var compontServices = arrayOf(
        "com.example.bus_login.service.UserServiceRegister"
    )

    override fun run() {
        //注册各个模块对外提供的服务.
        compontServices.forEach {
            try {
                val className = Class.forName(it)
                val baseServiceInit = className.newInstance() as BaseServiceInit
                baseServiceInit.init()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun isWaiting(): Boolean {
        return true
    }
}