package com.czy.yq_wanandroid

import com.czy.yq_wanandroid.entity.BaseResult
import com.czy.yq_wanandroid.entity.WxArticle
import com.czy.yq_wanandroid.mvpbase.IView

interface IMainView:IView {
    fun showMainView(resut: BaseResult<List<WxArticle>>)
}