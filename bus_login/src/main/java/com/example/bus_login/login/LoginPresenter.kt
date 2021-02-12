package com.czy.yq_wanandroid.business.login

import com.czy.business_base.api.WanApi
import com.czy.business_base.ext.commonSubscribe
import com.czy.business_base.ext.threadSwitchAndBindLifeCycle
import com.czy.lib_net.CommonApiService
import com.czy.business_base.mvpbase.MvpPresenter

class LoginPresenter : MvpPresenter<ILoginView>() {

    fun login(username: String, psw: String) {
        CommonApiService.getRequest(WanApi::class.java).login(username, psw)
//            .delay(3,TimeUnit.SECONDS)
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