    package com.czy.lib_webview

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.lib_base.ArouterConfig
import com.czy.lib_base.BaseActivity
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_webview.*


    @Route(path = ArouterConfig.webviewPath)
class WebViewActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun initView() {
        changNormalTopView(this, mTitleBar)
        var title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")!!
        mTitleBar.setTitle(title)
//        Thread {
//            AppDatabase.instance.readHistoryDao()
//                .addReadHistory(ReadHistory(url, title, System.currentTimeMillis()))
//            AppDatabase.instance.readHistoryDao().autoDeleteOverMax(READHISTORY_MAX_COUNT)
//        }.start()

        initWebView()
        mWebview.loadUrl(url)
    }

    private fun initWebView() {
        initWebViewSetting()
        mWebview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(webview: WebView?, progress: Int) {
                super.onProgressChanged(webview, progress)
//                progress.log()
                mProgressBar.visibility = View.VISIBLE
                mProgressBar.setProgress(progress)
            }
        }

        mWebview.webViewClient = object : WebViewClient() {

            override fun onPageFinished(webview: WebView?, url: String?) {
                super.onPageFinished(webview, url)
//                "pageFinished".log()
                mProgressBar.visibility = View.GONE

            }

            override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                return super.shouldOverrideUrlLoading(p0, p1)
            }
        }
    }

    override fun initData() {


    }

    fun initWebViewSetting() {
        val webSetting: WebSettings = mWebview.getSettings()
        webSetting.setAllowFileAccess(true)
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS)
        webSetting.setSupportZoom(true)
        webSetting.setBuiltInZoomControls(true)
        webSetting.setUseWideViewPort(true)
        webSetting.setSupportMultipleWindows(false)
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true)
        webSetting.setJavaScriptEnabled(true)
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        webSetting.setAppCachePath(getDir("appcache", 0).path)
        webSetting.setDatabasePath(getDir("databases", 0).path)
        webSetting.setGeolocationDatabasePath(getDir("geolocation", 0).path)
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND)
    }


    override fun onDestroy() {
        if(mWebview!=null){
            val parent: ViewGroup = mWebview.parent as ViewGroup
            parent.removeView(mWebview)
            mWebview.stopLoading()
            mWebview.settings.javaScriptEnabled = false
            mWebview.clearHistory()
            mWebview.clearView()
            mWebview.clearAnimation()
            mWebview.removeAllViews();
            mWebview.destroy()
        }
        super.onDestroy()

    }
}