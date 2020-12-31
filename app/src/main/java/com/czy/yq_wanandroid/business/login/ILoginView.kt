package com.czy.yq_wanandroid.business.login

import com.czy.lib_base.mvpbase.IView
import com.czy.yq_wanandroid.entity.UserInfo

interface ILoginView : IView {
    fun loginSuccess(userInfo: UserInfo)
    fun loginFailed(msg: String)
}