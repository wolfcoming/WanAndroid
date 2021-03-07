package com.czy.test_model

import com.czy.business_base.BaseActivity
import com.czy.business_base.dataSave.DataSaveProxy
import com.czy.lib_log.HiLog
import kotlinx.android.synthetic.main.activity_data_store.*

class DataStoreActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_data_store
    }

    override fun initView() {
    }

    override fun initData() {
        btnSaveGet.setOnClickListener {
            DataSaveProxy.getInstance().put("testString", "我是string的结果")
            DataSaveProxy.getInstance().put("testInt", 100)
            DataSaveProxy.getInstance().put("testLong", 100L)
            DataSaveProxy.getInstance().put("testFloat", 100f)
            DataSaveProxy.getInstance().put("testBoolean", true)

            getData()

        }

        btnRemove.setOnClickListener {
            DataSaveProxy.getInstance().remove("testString")
//            DataSaveProxy.getInstance().remove("testInt")
//            DataSaveProxy.getInstance().remove("testLong")
//            DataSaveProxy.getInstance().remove("testFloat")
//            DataSaveProxy.getInstance().remove("testBoolean")

            getData()
        }

        btnClear.setOnClickListener {
            DataSaveProxy.getInstance().clear()
            getData()
        }



    }

    fun getData(){
        val testBoolean = DataSaveProxy.getInstance().getBoolean("testBoolean", false)
        val testFloat = DataSaveProxy.getInstance().getFloat("testFloat", -1f)
        val testLong = DataSaveProxy.getInstance().getLong("testLong", -1L)
        val testInt = DataSaveProxy.getInstance().getInt("testInt", -1)
        val testString = DataSaveProxy.getInstance().getString("testString", "null")

        HiLog.e(testString)
        HiLog.e(testInt)
        HiLog.e(testLong)
        HiLog.e(testFloat)
        HiLog.e(testBoolean)
    }
}