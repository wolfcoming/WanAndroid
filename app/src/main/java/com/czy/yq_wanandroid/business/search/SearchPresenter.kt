package com.czy.yq_wanandroid.business.search

import com.czy.lib_base.mvpbase.MvpPresenter
import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.net.WanApiService
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitchAndBindLifeCycle

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
}