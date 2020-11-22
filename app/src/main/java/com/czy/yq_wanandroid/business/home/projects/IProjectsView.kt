package com.czy.yq_wanandroid.business.home.projects

import com.czy.yq_wanandroid.entity.ProjectEntity
import com.czy.yq_wanandroid.mvpbase.IView

interface IProjectsView : IView {
    fun showProjectsData(result: List<ProjectEntity>?)
    fun showFailePage(faileMsg: String?)
}