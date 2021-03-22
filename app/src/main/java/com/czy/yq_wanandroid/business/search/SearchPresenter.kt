package com.czy.yq_wanandroid.business.search

import com.czy.business_base.net.Transformer
import com.czy.business_base.api.WanApi
import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.entity.ArticleList
import com.czy.business_base.entity.HotKey
import com.czy.business_base.ext.threadSwitch
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.business_base.net.ApiSubscriberHelper
import com.czy.business_base.net.entity.BaseResult
import com.czy.lib_net.ApiException
import com.czy.lib_net.CommonApiService

class SearchPresenter : MvpPresenter<ISearchView>() {
    val searchModel = SearchModel()
    fun getHotKey() {
        CommonApiService.getRequest(WanApi::class.java).getHotKeys()
            .compose(Transformer.threadSwitch())
            .subscribe(object : ApiSubscriberHelper<BaseResult<List<HotKey>>>() {
                override fun onResult(t: BaseResult<List<HotKey>>) {
                    baseView?.showHotKeys(t.data!!)
                }

                override fun onFailed(msg: ApiException) {
                    baseView?.getHotKeysFailed(msg.message!!)
                }
            })
    }

    fun searchArticle(pageIndex: Int, words: String) {
        searchModel.searchArticle(pageIndex, words)
            .compose(Transformer.threadSwitchAndBindLifeCycle(baseView))
            .subscribe(object :ApiSubscriberHelper<ArticleList<ArticleEntity>>(){
                override fun onResult(t: ArticleList<ArticleEntity>) {
                    baseView?.showArticleList(t)
                }

                override fun onFailed(msg: ApiException) {
                    baseView?.getArticleListFailed(msg.message!!)
                }

            })
    }

    fun insertSearchHistory(name: String) {
        searchModel.insertSearchHistory(name)
    }

    fun getAllSearchHistory() {
        searchModel.getHistorySearch().subscribe {
            baseView?.showHistoryInput(it)
        }
    }

    fun clearSearchHistory() {
        searchModel.clearSearchHistory()
            .threadSwitch()
            .subscribe {
                getAllSearchHistory();
            }
    }
}

