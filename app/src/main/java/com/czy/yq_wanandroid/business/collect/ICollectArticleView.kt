package com.czy.yq_wanandroid.business.collect

import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.mvpbase.IView

interface ICollectArticleView:IView {
    fun showCollectArticle(datas:List<ArticleEntity>)
    fun showFailureView(msg:String)
}