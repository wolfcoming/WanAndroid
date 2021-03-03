package com.example.bus_login.collect

import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.mvpbase.IView

interface ICollectArticleView:IView {
    fun showCollectArticle(datas:List<ArticleEntity>)
    fun showFailureView(msg:String)
}