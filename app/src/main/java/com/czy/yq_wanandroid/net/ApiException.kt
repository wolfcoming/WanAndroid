package com.czy.yq_wanandroid.net

class ApiException(val code: Int, message: String) : Exception(message) {

    override fun toString(): String {
        return "ApiException(code=$code , message=$message)"
    }
}