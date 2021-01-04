package com.czy.yq_wanandroid.business.home.projects

import com.czy.lib_base.mvpbase.MvpPresenter
import com.czy.business_base.api.WanApiService
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitchAndBindLifeCycle

class ProjectsPrensenter : MvpPresenter<IProjectsView>() {
    fun getProjectsData() {
        WanApiService.getWanApi().getProjectsData()
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showProjectsData(it.data)
            }, {
                baseView?.showFailePage(it.message)
            })
    }
}