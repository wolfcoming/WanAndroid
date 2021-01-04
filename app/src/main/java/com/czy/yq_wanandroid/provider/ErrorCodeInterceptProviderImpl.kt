package com.czy.yq_wanandroid.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.business_base.service.ServiceFactory
import com.czy.lib_base.ArouterConfig
import com.czy.lib_base.Constants
import com.czy.lib_base.provider.IErrorCodeInterceptProvider
import com.infoholdcity.basearchitecture.self_extends.log

@Route(path = ArouterConfig.errorCodeProcess)
class ErrorCodeInterceptProviderImpl : IErrorCodeInterceptProvider {
    override fun processErrorCode(errorCode: Int, errorMsg: String) {
        "errorCode : $errorCode".log()
        if (errorCode == Constants.API_NEED_LOGIN) {
            //跳转登录
            ServiceFactory.getUserService().exitLogin()
            ServiceFactory.getUserService().gotoLogin()
        }
    }

    override fun init(context: Context?) {
        "ErrorCodeInterceptProviderImpl.init".log()
    }
}