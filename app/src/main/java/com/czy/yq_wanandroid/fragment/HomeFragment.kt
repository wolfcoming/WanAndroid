package com.czy.yq_wanandroid.fragment

import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.base.BaseFragment
import com.czy.yq_wanandroid.net.ApiErrorHandlerUtil
import com.czy.yq_wanandroid.net.WanApiService
import com.infoholdcity.basearchitecture.self_extends.log
import com.yangqing.record.ext.threadSwitch
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit

class HomeFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        tvTest.setOnClickListener {
            WanApiService.getWanApi().getWxarticle4()
                .delay(3, TimeUnit.SECONDS)
                .threadSwitch()
                .subscribe({
                    it.log()//成功结果回调打印
                }, {
                    val apiException = ApiErrorHandlerUtil.getWanApiException(it)
                    apiException.toString().log()
                })
        }
    }

    override fun initData() {
    }
}