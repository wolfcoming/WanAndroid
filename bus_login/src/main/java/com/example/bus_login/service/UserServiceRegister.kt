package com.example.bus_login.service

import com.czy.business_base.service.BaseServiceInit
import com.czy.business_base.service.ServiceFactory

class UserServiceRegister:BaseServiceInit {
    override fun init(){
        ServiceFactory.setUserService(UserServiceImpl())
    }
}