package com.czy.yq_wanandroid.business.projects

import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.mvpbase.IView

interface IProjectsList : IView {
    fun showProjectsListView(result: ArrayList<ArticleEntity>?)
    fun showFailureView(mes:String?)
}