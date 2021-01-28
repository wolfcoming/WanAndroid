package com.czy.lib_webview

import android.view.KeyEvent
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.business_base.service.ServiceFactory
import com.czy.lib_base.ArouterConfig
import com.czy.lib_base.BaseActivity
import com.czy.lib_base.utils.image.ImageUtils
import com.infoholdcity.basearchitecture.self_extends.log
import com.yangqing.record.ext.toast
import kotlinx.android.synthetic.main.activity_webview.*


@Route(path = ArouterConfig.webviewPath)
class WebViewActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }

    lateinit var mWebHolder: WebHolder
    override fun initView() {
        window.addFlags(SOFT_INPUT_STATE_VISIBLE)
        changNormalTopView(this, mTitleBar)
        var title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")!!
        mTitleBar.setTitle(title)
        ServiceFactory.getAppService().recodeReadHistory(url, title, System.currentTimeMillis())
        mWebHolder = WebHolder.with(this, mWebContainer, mProgressBar)
            .setOverrideUrlInterceptor {
                if (it.startsWith("http")) {
                    mWebHolder.loadUrl(it)
                }
                it.log("LLLLLLLL")
                return@setOverrideUrlInterceptor true
            }
        mWebHolder.setOnLongClickHitTestResult {
            if (HitResult.Type.IMAGE_TYPE == it.getType()) {
                val result = it.getResult()
                toast(result)
                return@setOnLongClickHitTestResult true
            }
            return@setOnLongClickHitTestResult false
        }
        mWebHolder.loadUrl(url)
    }


    override fun initData() {

    }


    override fun onDestroy() {
        mWebHolder.onDestroy()
        super.onDestroy()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (mWebHolder.handleKeyEvent(keyCode, event)) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}