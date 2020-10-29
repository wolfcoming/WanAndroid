package com.czy.yq_wanandroid.net

class BaseResult<T> {
    var errorCode: Int = 0
    var data: T? = null
    var errorMsg: String = ""
}