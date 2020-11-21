package com.czy.yq_wanandroid.business.login

import com.czy.yq_wanandroid.mvpbase.MvpPresenter
import com.czy.yq_wanandroid.net.WanApiService
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitchAndBindLifeCycle
import java.util.concurrent.TimeUnit

class LoginPresenter : MvpPresenter<ILoginView>() {

    fun login(username: String, psw: String) {
        WanApiService.getWanApi().login(username, psw)
            .delay(3,TimeUnit.SECONDS)
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