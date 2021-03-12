package com.czy.yq_wanandroid.business

import android.content.res.Configuration
import android.os.Handler
import com.czy.business_base.BaseActivity
import com.czy.business_base.MyActivityManager
import com.czy.business_base.dataSave.DataSaveProxy
import com.czy.yq_wanandroid.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        changNormalTopView(this, mTitleBar)
        sw_dark_mode.isChecked = isNightMode()
    }

    fun isNightMode(): Boolean {
        return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }

    }

    override fun initData() {
        sw_dark_mode.setOnCheckedChangeListener { buttonView, isChecked ->
            DataSaveProxy.getInstance().put("darkMode", isChecked)
            Handler().postDelayed({
                MyActivityManager.getActivityManager().reStartApp(this)
            },200)


//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
        }
    }
}