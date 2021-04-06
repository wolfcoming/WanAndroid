package com.czy.test_model

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.business_base.ArouterConfig
import com.czy.business_base.BaseActivity
import com.czy.business_base.ext.toast
import com.czy.lib_log.HiLog
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main_test.*

@Route(path = ArouterConfig.test_entry)
class TestMainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main_test
    }

    override fun initView() {
        btnDataSave.setOnClickListener {
            startActivity(Intent(TestMainActivity@ this, DataStoreActivity::class.java))
        }
        btnNetRequest.setOnClickListener {
            startActivity(Intent(TestMainActivity@ this, TestNetRequestActivity::class.java))
        }
        btnRxJava.setOnClickListener {
            startActivity(Intent(TestMainActivity@ this, RxJavaTestActivity::class.java))
        }
        btnUpgrade.setOnClickListener {
            val upgradeInfo = Beta.getUpgradeInfo()
            val currentVersion = packageManager.getPackageInfo(packageName, 0).versionCode
            if (upgradeInfo != null && currentVersion < upgradeInfo.versionCode) {
                toast("发现了新版本")
                Beta.checkAppUpgrade()
            } else {
                toast("暂无现版本")
            }
            HiLog.e(upgradeInfo)
        }
    }

    override fun initData() {
    }
}