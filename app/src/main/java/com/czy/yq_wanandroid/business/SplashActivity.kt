package com.czy.yq_wanandroid.business

import android.content.Intent
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_spalsh.*

class SplashActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_spalsh
    }

    override fun initView() {
        btnOne.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        btnTwo.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }
    }

    override fun initData() {
    }
}