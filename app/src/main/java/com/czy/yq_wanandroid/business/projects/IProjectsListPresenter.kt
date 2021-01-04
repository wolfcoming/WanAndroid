package com.czy.yq_wanandroid.business.projects

import com.czy.lib_base.mvpbase.MvpPresenter
import com.czy.business_base.entity.ArticleEntity
import com.czy.business_base.api.WanApiService
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitchAndBindLifeCycle

class IProjectsListPresenter : MvpPresenter<IProjectsList>() {
    fun getProjectsList(page: Int, cid: Int) {
        WanApiService.getWanApi().getProjectsListById(page, cid)
            .threadSwitchAndBindLifeCycle(baseView)
            .commonSubscribe({
                baseView?.showProjectsListView(it.data?.datas as ArrayList<ArticleEntity>)
            }) {
                baseView?.showFailureView(it.message)
            }
    }
}