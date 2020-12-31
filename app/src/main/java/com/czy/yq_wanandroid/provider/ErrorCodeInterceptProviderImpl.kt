package com.czy.yq_wanandroid.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.lib_base.ArouterConfig
import com.czy.lib_base.Constants
import com.czy.lib_base.provider.IErrorCodeInterceptProvider
import com.czy.yq_wanandroid.base.UserManage
import com.infoholdcity.basearchitecture.self_extends.log

@Route(path = ArouterConfig.errorCodeProcess)
class ErrorCodeInterceptProviderImpl : IErrorCodeInterceptProvider {
    override fun processErrorCode(errorCode: Int, errorMsg: String) {
        "errorCode : $errorCode".log()
        if (errorCode == Constants.API_NEED_LOGIN) {
            //跳转登录
            UserManage.exitLogin()
            UserManage.gotoLogin()
        }
    }

    override fun init(context: Context?) {
        "ErrorCodeInterceptProviderImpl.init".log()
    }
}