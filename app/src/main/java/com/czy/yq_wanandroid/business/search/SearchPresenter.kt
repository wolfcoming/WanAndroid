package com.czy.yq_wanandroid.business.search

import com.czy.lib_base.Constants.SEARCHHISTORY_MAX_COUNT
import com.czy.lib_base.mvpbase.MvpPresenter
import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.api.WanApiService
import com.czy.yq_wanandroid.room.AppDatabase
import com.czy.yq_wanandroid.room.entity.SearchHistory
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitch
import com.yangqing.record.ext.threadSwitchAndBindLifeCycle
import io.reactivex.rxjava3.core.Observable

class SearchPresenter : MvpPresenter<ISearchView>() {

    fun getHotKey() {
        WanApiService.getWanApi().getHotKeys()
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showHotKeys(it.data!!)
            }) {
                baseView?.getHotKeysFailed(it.message!!)
            }
    }

    fun searchArticle(pageIndex: Int, words: String) {
        WanApiService.getWanApi().search(pageIndex, words)
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showArticleList(it.data!!.datas as ArrayList<ArticleEntity>)
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
        }catch (e:Exception){
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
}