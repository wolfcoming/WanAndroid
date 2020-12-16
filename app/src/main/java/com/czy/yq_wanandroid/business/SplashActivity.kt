package com.czy.yq_wanandroid.business

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig
import com.czy.yq_wanandroid.R
import com.czy.lib_base.BaseActivity
import kotlinx.android.synthetic.main.activity_spalsh.*

class SplashActivity:  BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_spalsh
    }

    override fun initView() {
        btnOne.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        btnTwo.setOnClickListener {
            ARouter.getInstance().build(ArouterConfig.mainActivity).navigation()
//            startActivity(Intent(this,MainActivity2::class.java))
        }
    }

    override fun initData() {
    }
}