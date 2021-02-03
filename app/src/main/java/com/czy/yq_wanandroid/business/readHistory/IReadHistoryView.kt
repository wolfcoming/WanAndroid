package com.czy.yq_wanandroid.business.readHistory

import com.czy.business_base.mvpbase.IView
import com.czy.yq_wanandroid.room.entity.ReadHistory

interface IReadHistoryView : IView {
    fun showHistory(result: List<ReadHistory>)
    fun showLoadError(msg:String)
}