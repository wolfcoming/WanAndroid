package com.example.bus_login.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.business_base.ArouterConfig
import com.czy.business_base.entity.UserInfo
import com.czy.business_base.service.UserService
import com.example.bus_login.UserManage
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.auth.AuthService


@Route(path = ArouterConfig.userService)
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
        NIMClient.getService(AuthService::class.java).logout()
    }

    override fun gotoLogin() {
        UserManage.gotoLogin()
    }

    override fun init(context: Context?) {
    }
}