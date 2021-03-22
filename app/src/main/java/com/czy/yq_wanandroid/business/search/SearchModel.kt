package com.czy.yq_wanandroid.business.search

import com.czy.business_base.Constants
import com.czy.business_base.api.WanApi
import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.entity.ArticleList
import com.czy.business_base.entity.HotKey
import com.czy.business_base.ext.threadSwitch
import com.czy.business_base.mvpbase.BaseModel
import com.czy.business_base.net.entity.BaseResult
import com.czy.lib_net.CommonApiService
import com.czy.yq_wanandroid.room.AppDatabase
import com.czy.yq_wanandroid.room.entity.SearchHistory
import io.reactivex.rxjava3.core.Observable
import java.lang.IllegalArgumentException

class SearchModel : BaseModel() {

    fun getHistorySearch(): Observable<List<String>> {
        return Observable.create<List<String>> {
            val searchHistoryDao = AppDatabase.instance.searchHistoryDao()
            it.onNext(searchHistoryDao.getHistorySearch().map { it.name })
        }.threadSwitch()
    }

    fun clearSearchHistory(): Observable<String> {
        return Observable.create<String> {
            AppDatabase.instance.searchHistoryDao().delete()
            it.onNext("")
        }
    }

    fun insertSearchHistory(name: String) {
        if (name.isEmpty()) return
        try {
            Thread {
                AppDatabase.instance.searchHistoryDao()
                    .insertSearch(SearchHistory(name, System.currentTimeMillis()))
                AppDatabase.instance.searchHistoryDao()
                    .autoDelete(Constants.SEARCHHISTORY_MAX_COUNT)
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun searchArticle(pageIndex: Int, words: String): Observable<ArticleList<ArticleEntity>> {
        return CommonApiService.getRequest(WanApi::class.java).search(pageIndex, words).map {
            return@map it.data
        }
    }

    fun getHotKeys(): Observable<BaseResult<List<HotKey>>> {
        return  CommonApiService.getRequest(WanApi::class.java).getHotKeys()

    }
}