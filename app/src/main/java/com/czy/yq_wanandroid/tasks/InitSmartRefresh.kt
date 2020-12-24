package com.czy.yq_wanandroid.tasks

import com.czy.yq_wanandroid.launchstarter.task.Task
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class InitSmartRefresh : Task() {
    override fun needWait(): Boolean {
        return true
    }
    override fun run() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { mContext, layout ->
            ClassicsHeader(mContext)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { mContext, layout ->
            ClassicsFooter(mContext)
        }
    }

}