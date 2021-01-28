package com.czy.yq_wanandroid.business.home.projects

import com.czy.business_base.entity.ProjectEntity
import com.czy.lib_base.mvpbase.IView

interface IProjectsView : IView {
    fun showProjectsData(result: List<ProjectEntity>?)
    fun showFailePage(faileMsg: String?)
}