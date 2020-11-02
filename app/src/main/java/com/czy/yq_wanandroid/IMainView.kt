package com.czy.yq_wanandroid

import com.czy.yq_wanandroid.entity.WxArticle
import com.czy.yq_wanandroid.mvpbase.IView
import com.czy.yq_wanandroid.entity.BaseResult

interface IMainView:IView {
    fun showMainView(resut: BaseResult<List<WxArticle>>)
}