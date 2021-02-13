package com.czy.yq_wanandroid.business.home.answer

import com.czy.business_base.api.WanApi
import com.czy.business_base.ext.commonSubscribe
import com.czy.business_base.ext.threadSwitchAndBindLifeCycle
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.lib_net.CommonApiService

class AnswerPresenter : MvpPresenter<IAnswerView>() {
    fun getAnswerList(page: Int, fresh: Boolean) {
        CommonApiService.getRequest(WanApi::class.java).getAnswerList(page)
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showAnswerList(it.data?.datas!!, fresh)
            }) {
                baseView?.getDataFaile(it, fresh)
            }
    }
}