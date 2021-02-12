package com.czy.yq_wanandroid.business.search

import com.czy.lib_net.CommonApiService
import com.czy.business_base.Constants.SEARCHHISTORY_MAX_COUNT
import com.czy.business_base.api.WanApi
import com.czy.business_base.ext.commonSubscribe
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.yq_wanandroid.room.AppDatabase
import com.czy.yq_wanandroid.room.entity.SearchHistory
import com.czy.business_base.ext.threadSwitch
import com.czy.business_base.ext.threadSwitchAndBindLifeCycle
import io.reactivex.rxjava3.core.Observable

class SearchPresenter : MvpPresenter<ISearchView>() {

    fun getHotKey() {
        CommonApiService.getRequest(WanApi::class.java).getHotKeys()
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showHotKeys(it.data!!)
            }) {
                baseView?.getHotKeysFailed(it.message!!)
            }
    }

    fun searchArticle(pageIndex: Int, words: String) {
        CommonApiService.getRequest(WanApi::class.java).search(pageIndex, words)
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showArticleList(it.data!!)
            }) {
                baseView?.getArticleListFailed(it.message!!)
            }
    }

    fun insertSearchHistory(name: String) {
        if (name.isEmpty()) return
        try {
            Thread {
                AppDatabase.instance.searchHistoryDao()
                    .insertSearch(SearchHistory(name, System.currentTimeMillis()))
                AppDatabase.instance.searchHistoryDao().autoDelete(SEARCHHISTORY_MAX_COUNT)
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getAllSearchHistory() {
        Observable.create<List<String>> {
            val historySearch = AppDatabase.instance.searchHistoryDao().getHistorySearch()
            it.onNext(historySearch.map { it.name })
        }.threadSwitch()
            .subscribe {
                baseView?.showHistoryInput(it)
            }
    }

    fun clearSearchHistory() {
        Observable.create<List<String>> {
            AppDatabase.instance.searchHistoryDao().delete()
            val historySearch = AppDatabase.instance.searchHistoryDao().getHistorySearch()
            it.onNext(historySearch.map { it.name })
        }.threadSwitch()
            .subscribe {
                baseView?.showHistoryInput(it)
            }
    }
}