package com.czy.yq_wanandroid.business.collect

import com.czy.business_base.api.WanApi
import com.czy.lib_net.CommonApiService
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.business_base.ext.commonSubscribe
import com.czy.business_base.ext.threadSwitchAndBindLifeCycle

class CollectArticlePresenter : MvpPresenter<ICollectArticleView>() {
    fun getCollectArticleData(page: Int) {
        CommonApiService.getRequest(WanApi::class.java).getCollectArticleList(page)
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                it.data!!.datas.map {
                    it.collect = true
                }
                baseView?.showCollectArticle(it.data!!.datas)
            }, {
                baseView?.showFailureView(it.message!!)
            })
    }
}