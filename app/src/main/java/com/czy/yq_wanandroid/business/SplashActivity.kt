package com.czy.yq_wanandroid.business

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig
import com.czy.yq_wanandroid.R
import com.czy.lib_base.BaseActivity
import com.example.lib_imageloader.image.ImageLoaderManager
import kotlinx.android.synthetic.main.activity_spalsh.*

class SplashActivity:  BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_spalsh
    }

    override fun initView() {
        btnOne.setOnClickListener {
            ImageLoaderManager.getInstance().displayImageForCircle(iv,"https://f10.baidu.com/it/u1=294930612&u2=2144285941&fm=76")
        }

        btnTwo.setOnClickListener {
            ARouter.getInstance().build(ArouterConfig.mainActivity).navigation()
        }
    }

    override fun initData() {


    }
}