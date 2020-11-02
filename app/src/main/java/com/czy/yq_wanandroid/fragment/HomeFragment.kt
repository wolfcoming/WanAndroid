package com.czy.yq_wanandroid.fragment

import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.base.BaseFragment
import com.czy.yq_wanandroid.net.WanApiService
import com.infoholdcity.basearchitecture.self_extends.log
import com.yangqing.record.ext.commonSubscribe
import com.yangqing.record.ext.threadSwitch
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        tvTest.setOnClickListener {
            WanApiService.getWanApi().getWxarticle4()
                .threadSwitch()
                .commonSubscribe({
                    it.log()
                }, {
                    it.toString().log()
                },{
                    "onFinish".log()
                })
        }
    }

    override fun initData() {
    }
}


