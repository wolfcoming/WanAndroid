package com.czy.business_base.tasks

import com.czy.business_base.launchstarter.task.Task
import com.czy.business_base.service.BaseServiceInit

/**
 * 初始化个组件服务
 */
class InitCompontService : Task() {
    var compontServices = arrayOf(
        "com.example.bus_login.service.UserServiceRegister",
        "com.czy.yq_wanandroid.service.AppServiceRegister"
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