package com.czy.business_base.intercept

import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.business_base.ArouterConfig
import com.czy.business_base.BaseActivity
import com.czy.business_base.R

/**
 *
 * @author yangqing
 * @time   3/4/21 2:46 PM
 * @describe 路由降级界面
 */
@Route(path = ArouterConfig.lost_page)
class DemotionActivity :BaseActivity(){
    override fun getLayoutId(): Int {
        return R.layout.activity_demotion_activity
    }

    override fun initView() {
    }

    override fun initData() {
    }
}