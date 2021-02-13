package com.czy.yq_wanandroid.business.readHistory

import com.czy.business_base.ext.threadSwitch
import com.czy.business_base.mvpbase.MvpPresenter
import com.czy.yq_wanandroid.room.AppDatabase
import com.czy.yq_wanandroid.room.entity.ReadHistory
import com.infoholdcity.basearchitecture.self_extends.log
import io.reactivex.rxjava3.core.Observable

class ReadHistoryPresenter : MvpPresenter<IReadHistoryView>() {
    fun getHistory(offset: Int, pageSize: Int) {
        Observable.create<List<ReadHistory>> {
            it.onNext(AppDatabase.instance.readHistoryDao().getReadHistory(offset, pageSize))
        }
            .threadSwitch()
            .subscribe( {
                it.log()
                baseView?.showHistory(it)
            },{
                it.printStackTrace()
                baseView?.showLoadError("数据加载失败")
            })
    }
}