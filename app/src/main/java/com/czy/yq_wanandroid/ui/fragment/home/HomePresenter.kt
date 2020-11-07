package com.czy.yq_wanandroid.ui.fragment.home

import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.entity.ArticleList
import com.czy.yq_wanandroid.entity.Banner
import com.czy.yq_wanandroid.entity.BaseResult
import com.czy.yq_wanandroid.mvpbase.MvpPresenter
import com.czy.yq_wanandroid.net.WanApiService
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitchAndBindLifeCycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Function3
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomePresenter : MvpPresenter<IHomeView>() {

    fun getHomeData(page: Int, fresh: Boolean = false) {
        var pageNumber: Int = if (fresh) {
            0
        } else page
        if (pageNumber == 0) {
            Observable.zip(
                WanApiService.getWanApi().getTopArticle(),
                WanApiService.getWanApi().getHomeArticle(pageNumber),
                WanApiService.getWanApi().getHomeBanner(),
                Function3<BaseResult<List<ArticleEntity>>,
                        BaseResult<ArticleList<ArticleEntity>>,
                        BaseResult<List<Banner>>,
                        HashMap<String, Any>>
                { topResult, articleList, bannerList ->
                    val map: HashMap<String, Any> = HashMap()
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
                    map.put("article", list)
                    map.put("banner", bannerList.data!!)
                    map
                })
                .threadSwitchAndBindLifeCycle(baseView)
                .commonSubscribe({
                    val list: ArrayList<ArticleEntity> =
                        it.get("article") as ArrayList<ArticleEntity>
                    val bannerList: ArrayList<Banner> = it.get("banner") as ArrayList<Banner>

                    baseView?.showData(list, bannerList, fresh)
                }, {
                    baseView?.getDataFail(it,fresh)
                })
        } else {
            WanApiService.getWanApi().getHomeArticle(pageNumber)
                .threadSwitchAndBindLifeCycle(baseView)
                .commonSubscribe({
                    baseView?.showData(it.data?.datas,null,fresh)
                }, {
                    baseView?.getDataFail(it,fresh)
                })
        }
    }


}