package com.czy.yq_wanandroid.business.search

import com.czy.business_base.api.Transformer
import com.czy.business_base.api.WanApi
import com.czy.business_base.entity.HotKey
import com.czy.business_base.ext.threadSwitch
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.business_base.net.ApiSubscriberHelper
import com.czy.business_base.net.entity.BaseResult
import com.czy.lib_net.ApiException
import com.czy.lib_net.CommonApiService
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.observers.DisposableObserver

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


//        CommonApiService.getRequest(WanApi::class.java).getHotKeys()
//            .compose(Transformer.threadSwitch())
//            .subscribe(object : ApiSubscriberHelper<BaseResult<HotKey>>() {
//                override fun onResult(t: BaseResult<HotKey>?) {
//                }
//
//                override fun onFailed(msg: ApiException?) {
//
//                }
//            })
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

