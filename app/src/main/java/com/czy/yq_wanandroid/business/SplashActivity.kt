package com.czy.yq_wanandroid.business

import com.alibaba.android.arouter.launcher.ARouter
import com.czy.lib_base.ArouterConfig
import com.czy.lib_base.BaseActivity
import com.czy.yq_wanandroid.R
import kotlinx.android.synthetic.main.activity_spalsh.*

class SplashActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_spalsh
    }

    override fun initView() {
        btnTwo.setOnClickListener {
            ARouter.getInstance().build(ArouterConfig.mainActivity).navigation()
        }
    }

    override fun initData() {

    }
}