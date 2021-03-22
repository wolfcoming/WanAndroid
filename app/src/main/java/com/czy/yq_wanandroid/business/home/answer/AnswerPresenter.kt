package com.czy.yq_wanandroid.business.home.answer

import com.czy.business_base.api.WanApi
import com.czy.business_base.ext.threadSwitchAndBindLifeCycle
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.business_base.net.Transformer
import com.czy.lib_net.ApiException
import com.czy.lib_net.CommonApiService

class AnswerPresenter : MvpPresenter<IAnswerView>() {
    fun getAnswerList(page: Int, fresh: Boolean) {
        CommonApiService.getRequest(WanApi::class.java).getAnswerList(page)
            .threadSwitchAndBindLifeCycle(baseView)
            .compose(Transformer.serverCodeDeal())
            .subscribe({
                baseView?.showAnswerList(it.data?.datas!!, fresh)
            }) {
                baseView?.getDataFaile(it as ApiException, fresh)
            }
    }
}