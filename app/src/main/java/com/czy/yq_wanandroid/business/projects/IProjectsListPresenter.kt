package com.czy.yq_wanandroid.business.projects

import com.czy.business_base.api.WanApi
import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.ext.threadSwitchAndBindLifeCycle
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.lib_net.CommonApiService

class IProjectsListPresenter : MvpPresenter<IProjectsList>() {
    fun getProjectsList(page: Int, cid: Int) {
        CommonApiService.getRequest(WanApi::class.java).getProjectsListById(page, cid)
            .threadSwitchAndBindLifeCycle(baseView)
            .subscribe({
                baseView?.showProjectsListView(it.data?.datas as ArrayList<ArticleEntity>)
            }) {
                baseView?.showFailureView(it.message)
            }
    }
}