package com.czy.business_base.intercept

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig

@Route(path = "/lost/degrade")
class DegradeServiceImpl : DegradeService {
    override fun init(context: Context?) {

    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        //找不到对应页面时--跳转到统一界面
        ARouter.getInstance().build(ArouterConfig.lost_page).greenChannel().navigation()
    }
}