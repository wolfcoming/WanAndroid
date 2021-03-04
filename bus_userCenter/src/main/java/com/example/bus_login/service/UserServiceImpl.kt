package com.example.bus_login.service

import android.content.Context
import com.czy.business_base.entity.UserInfo
import com.czy.business_base.service.UserService
import com.example.bus_login.UserManage

class UserServiceImpl:UserService {
    override fun getUserInfo(): UserInfo? {
        return UserManage.getUserInfo()
    }

    override fun isLogin(): Boolean {
        return UserManage.isLogin()
    }

    override fun checkLogin(context: Context, function: () -> Unit) {
        UserManage.checkLogin(context,function)
    }

    override fun exitLogin() {
        UserManage.exitLogin()
    }

    override fun gotoLogin() {
        UserManage.gotoLogin()
    }
}