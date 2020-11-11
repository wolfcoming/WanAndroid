package com.czy.yq_wanandroid.ui.fragment.answer

import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.mvpbase.IView
import com.czy.yq_wanandroid.net.ApiException

interface IAnswerView : IView {
    fun showAnswerList(result: List<ArticleEntity>?, fresh: Boolean)
    fun getDataFaile(e: ApiException, fresh: Boolean)
}