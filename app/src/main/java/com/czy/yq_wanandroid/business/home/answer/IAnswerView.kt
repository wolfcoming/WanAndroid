package com.czy.yq_wanandroid.business.home.answer

import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.lib_base.mvpbase.IView
import com.czy.lib_base.net.ApiException

interface IAnswerView : IView {
    fun showAnswerList(result: List<ArticleEntity>?, fresh: Boolean)
    fun getDataFaile(e: ApiException, fresh: Boolean)
}