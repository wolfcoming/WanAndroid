package com.czy.yq_wanandroid.business.collect

import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.lib_base.mvpbase.IView

interface ICollectArticleView:IView {
    fun showCollectArticle(datas:List<ArticleEntity>)
    fun showFailureView(msg:String)
}