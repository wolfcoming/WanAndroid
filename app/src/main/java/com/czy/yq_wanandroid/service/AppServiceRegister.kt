package com.czy.yq_wanandroid.service

import com.czy.business_base.service.BaseServiceInit
import com.czy.business_base.service.ServiceFactory

class AppServiceRegister:BaseServiceInit {

    override fun init() {
        ServiceFactory.setAppService(AppServiceImpl())
    }
}