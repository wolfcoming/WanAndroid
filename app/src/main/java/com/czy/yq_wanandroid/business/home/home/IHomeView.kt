package com.czy.yq_wanandroid.business.home.home

import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.entity.Banner
import com.czy.business_base.mvpbase.IView
import com.czy.business_base.net.ApiException

interface IHomeView : IView {
    fun showData(articleList: List<ArticleEntity>?, bannerList: List<Banner>?,fresh:Boolean)
    fun getDataFail(e:ApiException,fresh: Boolean)
}