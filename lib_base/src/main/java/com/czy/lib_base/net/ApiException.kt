package com.czy.lib_base.net

open class ApiException(val code: Int, message: String) : Exception(message) {

    override fun toString(): String {
        return "ApiException(code=$code , message=$message)"
    }
}