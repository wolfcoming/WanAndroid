package com.czy.yq_wanandroid.ui.fragment.projects

import com.czy.yq_wanandroid.mvpbase.MvpPresenter
import com.czy.yq_wanandroid.net.WanApiService
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