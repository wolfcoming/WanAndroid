package com.czy.business_base.service

import android.content.Context
import com.czy.business_base.entity.UserInfo

interface UserService {
    fun getUserInfo(): UserInfo?
    fun isLogin():Boolean
    fun checkLogin(context: Context, function: () -> Unit)
    fun exitLogin()
    fun gotoLogin()
}