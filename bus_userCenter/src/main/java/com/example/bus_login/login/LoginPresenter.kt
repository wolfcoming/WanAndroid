package com.czy.yq_wanandroid.business.login

import com.czy.business_base.api.WanApi
import com.czy.business_base.ext.commonSubscribe
import com.czy.business_base.ext.threadSwitchAndBindLifeCycle
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.lib_net.CommonApiService

class LoginPresenter : MvpPresenter<ILoginView>() {

    fun login(username: String, psw: String) {
        CommonApiService.getRequest(WanApi::class.java).login(username, psw)
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showLoading()
            }, {
                baseView?.loginSuccess(it.data!!)
            }, {
                baseView?.loginFailed(it.message!!)
            }, {
                baseView?.hideLoading()
            })
    }
}