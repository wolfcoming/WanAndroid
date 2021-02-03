package com.czy.yq_wanandroid.business.collect

import com.czy.business_base.api.WanApiService
import com.czy.business_base.mvpbase.MvpPresenter
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitchAndBindLifeCycle

class CollectArticlePresenter : MvpPresenter<ICollectArticleView>() {
    fun getCollectArticleData(page: Int) {
        WanApiService.getWanApi().getCollectArticleList(page)
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