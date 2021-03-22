package com.example.bus_login.collect

import com.czy.business_base.net.Transformer
import com.czy.business_base.api.WanApi
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.lib_net.CommonApiService

class CollectArticlePresenter : MvpPresenter<ICollectArticleView>() {
    fun getCollectArticleData(page: Int) {
        CommonApiService.getRequest(WanApi::class.java)
            .getCollectArticleList(page)
            .compose(Transformer.threadSwitchAndBindLifeCycle(baseView))
            .compose(Transformer.serverCodeDeal(false))
            .subscribe({
                it.data!!.datas.map {
                    it.collect = true
                }
                baseView?.showCollectArticle(it.data!!.datas)
            }, {
                baseView?.showFailureView(it.message!!)
            })
    }
}