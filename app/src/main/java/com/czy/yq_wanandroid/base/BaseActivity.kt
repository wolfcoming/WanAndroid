package com.czy.yq_wanandroid.base

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.czy.yq_wanandroid.common.LoadingDialog
import com.czy.yq_wanandroid.utils.display.DisplayInfoUtils
import com.infoholdcity.basearchitecture.self_extends.log
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus


abstract class BaseActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "${this.javaClass.simpleName}:  onCreate".log()
        //去除状态栏区域，让contentView能够展示在状态栏里边
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this)
        }
        setContentView(getLayoutId())
        initViewBefore()
        initView()
        initData()
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData()
    open fun initViewBefore() {}

    open fun changNormalTopView(context: Context, topView: View) {
        if (context == null || topView == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        val statusBarHeight: Int = DisplayInfoUtils.getInstance().statusBarHeight
        val params = topView.layoutParams as ViewGroup.MarginLayoutParams
        params.height += statusBarHeight
        topView.layoutParams = params
        topView.setPadding(
            topView.paddingLeft,
            topView.paddingTop + statusBarHeight,
            topView.paddingRight,
            topView.paddingBottom
        )
    }

    /**
     * 是否注册事件分发，默认不绑定
     */
    protected open fun isRegisterEventBus(): Boolean {
        return false
    }


    var loadingDialog: LoadingDialog? = null

    open fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.Builder(this)
                .setLoadingMsg("加载中...")
                .setCanCancel(false)
                .setDemines(0.1f)
                .build()
        }
        loadingDialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    open fun hideLoading() {
        loadingDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        "${this.javaClass.simpleName}:  onStart".log()
    }

    override fun onResume() {
        super.onResume()
        "${this.javaClass.simpleName}:  onResume".log()
    }

    override fun onPause() {
        super.onPause()
        "${this.javaClass.simpleName}:  onPause".log()
    }

    override fun onStop() {
        super.onStop()
        "${this.javaClass.simpleName}:  onStop".log()
    }

    override fun onDestroy() {
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
        "${this.javaClass.simpleName}:  onDestroy".log()
    }

    override fun onRestart() {
        super.onRestart()
        "${this.javaClass.simpleName}:  onRestart".log()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        "${this.javaClass.simpleName}:  onSaveInstanceState".log()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        "${this.javaClass.simpleName}:  onRestoreInstanceState".log()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        "${this.javaClass.simpleName}:  onConfigurationChanged".log()
    }

    override fun finish() {
        hideLoading()
        super.finish()
    }
}