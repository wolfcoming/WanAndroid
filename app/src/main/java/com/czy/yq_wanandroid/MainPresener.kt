package com.czy.yq_wanandroid

import com.czy.yq_wanandroid.mvpbase.MvpPresenter
import com.czy.yq_wanandroid.net.WanApiService
import com.infoholdcity.basearchitecture.self_extends.log
import com.yangqing.record.ext.threadSwitch
import java.util.concurrent.TimeUnit

class MainPresener : MvpPresenter<IMainView>() {
    fun getMainData() {
        WanApiService.getWanApi().getWxarticle4()
            .delay(3, TimeUnit.SECONDS)
            .threadSwitch()
            .compose(baseView?.bindLifecycleEvent())
            .subscribe({
                it.log()//成功结果回调打印
            }, {
                it.printStackTrace()//异常打印
            })
    }
}