package com.czy.yq_wanandroid.business.search

import com.czy.lib_base.mvpbase.IView
import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.entity.HotKey

interface ISearchView : IView {
    fun showHotKeys(list: List<HotKey>)
    fun getHotKeysFailed(msg: String)
    fun showHistoryInput(list: List<String>)

    fun showArticleList(list: ArrayList<ArticleEntity>)
    fun getArticleListFailed(msg: String)
}