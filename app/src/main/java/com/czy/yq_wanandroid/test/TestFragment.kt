package com.czy.yq_wanandroid.test

import com.czy.yq_wanandroid.IMainView
import com.czy.yq_wanandroid.MainPresener
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.entity.BaseResult
import com.czy.yq_wanandroid.entity.WxArticle
import com.czy.yq_wanandroid.mvpbase.MvpFragment
import com.infoholdcity.basearchitecture.self_extends.log
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : MvpFragment<MainPresener>(), IMainView {
    override fun getLayoutId(): Int {
        return R.layout.fragment_test
    }

    override fun initView() {
        btnNet.setOnClickListener {
           mPresenter?.getMainData()
        }
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".log()
    }

    override fun createPresenter(): MainPresener {
        return MainPresener()
    }

    override fun showMainView(resut: BaseResult<List<WxArticle>>) {
        resut.log<BaseResult<List<WxArticle>>>()
    }

}