package com.czy.yq_wanandroid.business.home.home

import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.entity.Banner
import com.czy.lib_base.mvpbase.IView
import com.czy.lib_base.net.ApiException

interface IHomeView : IView {
    fun showData(articleList: List<ArticleEntity>?, bannerList: List<Banner>?,fresh:Boolean)
    fun getDataFail(e:ApiException,fresh: Boolean)
}