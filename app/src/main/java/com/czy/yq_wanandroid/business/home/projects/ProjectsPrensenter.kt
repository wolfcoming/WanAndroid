package com.czy.yq_wanandroid.business.home.projects

import com.czy.business_base.api.WanApi
import com.czy.business_base.ext.commonSubscribe
import com.czy.business_base.ext.threadSwitchAndBindLifeCycle
import com.czy.lib_net.CommonApiService
import com.czy.business_base.mvpbase.MvpPresenter

class ProjectsPrensenter : MvpPresenter<IProjectsView>() {
    fun getProjectsData() {
        CommonApiService.getRequest(WanApi::class.java).getProjectsData()
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showProjectsData(it.data)
            }, {
                baseView?.showFailePage(it.message)
            })
    }
}