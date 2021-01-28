package com.czy.yq_wanandroid.business.home.answer

import com.czy.business_base.api.WanApiService
import com.czy.lib_base.mvpbase.MvpPresenter
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitchAndBindLifeCycle

class AnswerPresenter : MvpPresenter<IAnswerView>() {
    fun getAnswerList(page: Int, fresh: Boolean) {
        WanApiService.getWanApi().getAnswerList(page)
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showAnswerList(it.data?.datas!!, fresh)
            }) {
                baseView?.getDataFaile(it, fresh)
            }
    }
}