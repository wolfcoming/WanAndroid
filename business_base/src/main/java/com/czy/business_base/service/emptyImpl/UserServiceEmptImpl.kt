package com.czy.business_base.service.emptyImpl

import android.content.Context
import com.czy.business_base.entity.UserInfo
import com.czy.business_base.service.UserService

class UserServiceEmptImpl:UserService {
    override fun getUserInfo(): UserInfo?{
        return null
    }

    override fun isLogin(): Boolean {
        return false
    }

    override fun checkLogin(context: Context, function: () -> Unit) {

    }

    override fun exitLogin() {

    }

    override fun gotoLogin() {
    }
}