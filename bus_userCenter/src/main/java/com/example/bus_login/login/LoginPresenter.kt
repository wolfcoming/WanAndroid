package com.czy.yq_wanandroid.business.login

import com.czy.business_base.api.WanApi
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.business_base.net.Transformer
import com.czy.lib_net.CommonApiService

class LoginPresenter : MvpPresenter<ILoginView>() {

    fun login(username: String, psw: String) {
        baseView?.showLoading()
        CommonApiService.getRequest(WanApi::class.java).login(username, psw)
            .compose(Transformer.threadSwitchAndBindLifeCycle(baseView))
            .compose(Transformer.serverCodeDeal())
            .subscribe({
                baseView?.loginSuccess(it.data!!)
            }, {
                baseView?.loginFailed(it.message!!)
            }, {
                baseView?.hideLoading()
            })
    }
}
