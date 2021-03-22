package com.czy.business_base.net

import com.czy.business_base.Constants
import com.czy.business_base.net.entity.BaseResult
import com.czy.business_base.service.ServiceFactory.Companion.getUserService

object SpecialCodeDeal {
    fun <T> dealSpecialCode(t: BaseResult<T>) {
        //处理需要登录错误码
        if (Constants.API_NEED_LOGIN == t.errorCode) {
            getUserService().gotoLogin()
        }
    }
}