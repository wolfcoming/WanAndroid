package com.czy.yq_wanandroid.business.collect

import com.czy.yq_wanandroid.mvpbase.MvpPresenter
import com.czy.yq_wanandroid.net.WanApiService
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