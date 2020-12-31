package com.czy.yq_wanandroid.business.projects

import com.czy.lib_base.mvpbase.IView
import com.czy.yq_wanandroid.entity.ArticleEntity

interface IProjectsList : IView {
    fun showProjectsListView(result: ArrayList<ArticleEntity>?)
    fun showFailureView(mes:String?)
}