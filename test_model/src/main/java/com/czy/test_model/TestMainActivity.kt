package com.czy.test_model

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.business_base.ArouterConfig
import com.czy.business_base.BaseActivity
import kotlinx.android.synthetic.main.activity_main_test.*

@Route(path = ArouterConfig.test_entry)
class TestMainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main_test
    }

    override fun initView() {
        btnDataSave.setOnClickListener {
            startActivity(Intent(TestMainActivity@this,DataStoreActivity::class.java))
        }
        btnNetRequest.setOnClickListener {
            startActivity(Intent(TestMainActivity@this,TestNetRequestActivity::class.java))
        }
        btnRxJava.setOnClickListener {
            startActivity(Intent(TestMainActivity@this,RxJavaTestActivity::class.java))
        }
    }

    override fun initData() {
    }
}