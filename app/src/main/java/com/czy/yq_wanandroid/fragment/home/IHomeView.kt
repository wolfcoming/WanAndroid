package com.czy.yq_wanandroid.fragment.home

import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.entity.Banner
import com.czy.yq_wanandroid.mvpbase.IView

interface IHomeView : IView {
    fun showBannerView(result: List<Banner>?)
    fun showArticleList(result: List<ArticleEntity>?, fresh:Boolean)
}