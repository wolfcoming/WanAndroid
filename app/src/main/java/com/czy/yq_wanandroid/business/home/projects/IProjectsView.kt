package com.czy.yq_wanandroid.business.home.projects

import com.czy.lib_base.mvpbase.IView
import com.czy.yq_wanandroid.entity.ProjectEntity

interface IProjectsView : IView {
    fun showProjectsData(result: List<ProjectEntity>?)
    fun showFailePage(faileMsg: String?)
}