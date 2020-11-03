package com.czy.yq_wanandroid.fragment

import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.base.BaseFragment
import com.yangqing.record.ext.toast
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        changNormalTopView(context!!, mTitleBar)
        mTitleBar.leftClickListener {
            toast("left click")
        }

        mTitleBar.rightClickListener {
            toast("right")
        }
    }

    override fun initData() {
    }
}


