package com.czy.business_base.provider

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 错误码拦截
 */
interface IErrorCodeInterceptProvider : IProvider {
    fun processErrorCode(errorCode: Int, errorMsg: String)
}