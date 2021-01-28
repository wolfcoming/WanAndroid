package com.czy.yq_wanandroid.business.search

import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.entity.ArticleList
import com.czy.business_base.entity.HotKey
import com.czy.lib_base.mvpbase.IView

interface ISearchView : IView {
    fun showHotKeys(list: List<HotKey>)
    fun getHotKeysFailed(msg: String)
    fun showHistoryInput(list: List<String>)

    fun showArticleList(list: ArticleList<ArticleEntity>)
    fun getArticleListFailed(msg: String)
}