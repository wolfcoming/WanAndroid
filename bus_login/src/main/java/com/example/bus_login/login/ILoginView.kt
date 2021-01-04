package com.czy.yq_wanandroid.business.login

import com.czy.business_base.entity.UserInfo
import com.czy.lib_base.mvpbase.IView


interface ILoginView : IView {
    fun loginSuccess(userInfo: UserInfo)
    fun loginFailed(msg: String)
}