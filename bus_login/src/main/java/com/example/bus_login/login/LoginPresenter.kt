package com.czy.yq_wanandroid.business.login

import com.czy.business_base.api.WanApiService
import com.czy.lib_base.mvpbase.MvpPresenter
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitchAndBindLifeCycle

class LoginPresenter : MvpPresenter<ILoginView>() {

    fun login(username: String, psw: String) {
        WanApiService.getWanApi().login(username, psw)
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