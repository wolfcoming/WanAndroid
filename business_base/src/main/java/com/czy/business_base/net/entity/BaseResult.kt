package com.czy.business_base.net.entity

import com.czy.lib_base.BuildConfig
import com.google.gson.Gson

class BaseResult<T> : BaseEntity() {
    var errorCode: Int = 0
    var data: T? = null
    var errorMsg: String = ""
    override fun toString(): String {
        if (BuildConfig.DEBUG) {
            return Gson().toJson(this)
        }
        return super.toString()
    }

}