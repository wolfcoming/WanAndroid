package com.czy.yq_wanandroid.business.home.answer

import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.mvpbase.IView
import com.czy.lib_net.ApiException

interface IAnswerView : IView {
    fun showAnswerList(result: List<ArticleEntity>?, fresh: Boolean)
    fun getDataFaile(e: ApiException, fresh: Boolean)
}