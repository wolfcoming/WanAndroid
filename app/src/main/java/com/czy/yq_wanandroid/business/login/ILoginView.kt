package com.czy.yq_wanandroid.business.login

import com.czy.yq_wanandroid.entity.UserInfo
import com.czy.yq_wanandroid.mvpbase.IView

interface ILoginView : IView {
    fun loginSuccess(userInfo: UserInfo)
    fun loginFailed(msg: String)
}