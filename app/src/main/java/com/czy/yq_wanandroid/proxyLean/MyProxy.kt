package com.czy.yq_wanandroid.proxyLean

import com.infoholdcity.basearchitecture.self_extends.log
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class MyProxy : InvocationHandler {
    private var target: Any? = null

    constructor(target: Subject) {
        this.target = target
    }


    /**
     * @param proxy 指代我们所代理的那个真实对象
     * @param method 指代的是我们所要调用真实对象的某 个方法的Method对象。
     * @param args 指代的是调用真实对象某个方法时接受的参数。
     */
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        "proxy ${proxy!!::class.java.name}".log()
        "before".log()
        method?.invoke(target, args)
        "after".log()
        return null
    }
}