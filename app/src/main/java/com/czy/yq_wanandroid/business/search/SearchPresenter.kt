package com.czy.yq_wanandroid.business.search

import com.czy.business_base.api.Transformer
import com.czy.business_base.api.WanApi
import com.czy.business_base.ext.threadSwitch
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.lib_net.CommonApiService

class SearchPresenter : MvpPresenter<ISearchView>() {
    val searchModel = SearchModel()
    fun getHotKey() {
        searchModel.getHotKeys()
            .compose(Transformer.threadSwitchAndBindLifeCycle(baseView))
            .subscribe({
                baseView?.showHotKeys(it.data!!)
            }) {
                baseView?.getHotKeysFailed(it.message!!)
            }
    }

    fun searchArticle(pageIndex: Int, words: String) {
        searchModel.searchArticle(pageIndex, words)
            .compose(Transformer.threadSwitchAndBindLifeCycle(baseView))
            .subscribe({
                baseView?.showArticleList(it)
            }) {
                baseView?.getArticleListFailed(it.message!!)
            }
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