package com.czy.yq_wanandroid.entity

import com.czy.yq_wanandroid.BuildConfig
import com.google.gson.Gson

class BaseResult<T> {
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