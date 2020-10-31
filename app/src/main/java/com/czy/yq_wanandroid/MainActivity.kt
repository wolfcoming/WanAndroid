package com.czy.yq_wanandroid

import android.content.Intent
import com.czy.yq_wanandroid.entity.WxArticle
import com.czy.yq_wanandroid.mvpbase.MvpActivity
import com.czy.yq_wanandroid.net.BaseResult
import com.czy.yq_wanandroid.test.TestFragment
import com.yangqing.record.ext.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpActivity<MainPresener>(), IMainView {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.mContainer, TestFragment())
        beginTransaction.commit()
        btnTest.setOnClickListener { mPresenter?.getMainData() }
        btnTest2.setOnClickListener {
            startActivity(
                Intent(
                    MainActivity@ this,
                    MainActivity::class.java
                )
            )
        }
    }

    override fun initData() {

    }

    override fun createPresenter(): MainPresener {
        return MainPresener()
    }


    override fun showMainView(resut: BaseResult<List<WxArticle>>) {
        toast(resut.toString())
    }

}