package com.czy.yq_wanandroid.fragment.home

import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.entity.ArticleList
import com.czy.yq_wanandroid.entity.BaseResult
import com.czy.yq_wanandroid.mvpbase.MvpPresenter
import com.czy.yq_wanandroid.net.WanApiService
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitchAndBindLifeCycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import java.util.*

class HomePresenter : MvpPresenter<IHomeView>() {

    fun getBannerData() {
        WanApiService.getWanApi().getHomeBanner()
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showBannerView(it.data)
            }, {
                baseView?.showToast(it.message)
            })
    }

    fun getArticleList(page: Int, fresh: Boolean = false) {
        var pageNumber: Int = if (fresh) {
            0
        } else page
        if (pageNumber == 0) {
            Observable.zip(
                WanApiService.getWanApi().getTopArticle(),
                WanApiService.getWanApi().getHomeArticle(pageNumber),
                BiFunction<BaseResult<List<ArticleEntity>>, BaseResult<ArticleList<ArticleEntity>>, List<ArticleEntity>>
                { topResult, articleList ->
                    val list: MutableList<ArticleEntity> = ArrayList()
                    if (topResult.data != null) {
                        topResult.data?.map {
                            it.top = true
                        }
                        list.addAll(topResult.data!!)
                    }
                    if (articleList.data != null) {
                        list.addAll(articleList.data?.datas!!)
                    }
                    list
                })
                .threadSwitchAndBindLifeCycle(baseView)
                .commonSubscribe {
                    baseView?.showArticleList(it, true)
                }
        } else {
            WanApiService.getWanApi().getHomeArticle(pageNumber)
                .threadSwitchAndBindLifeCycle(baseView)
                .commonSubscribe {
                    baseView?.showArticleList(it.data?.datas, false)
                }
        }
    }


}