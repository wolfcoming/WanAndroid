package com.czy.yq_wanandroid.business.readHistory

import com.czy.yq_wanandroid.mvpbase.MvpPresenter
import com.czy.yq_wanandroid.room.AppDatabase
import com.czy.yq_wanandroid.room.entity.ReadHistory
import com.infoholdcity.basearchitecture.self_extends.log
import com.yangqing.record.ext.threadSwitch
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
                baseView?.showLoadError("数据加载失败")
            })
    }
}