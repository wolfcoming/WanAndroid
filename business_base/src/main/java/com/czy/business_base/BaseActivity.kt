package com.czy.business_base

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.czy.lib_base.utils.display.DisplayInfoUtils
import com.czy.lib_log.HiLogManager
import com.czy.lib_log.printer.HiViewPrinter
import com.gyf.immersionbar.ImmersionBar
import com.infoholdcity.basearchitecture.self_extends.log
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.umeng.message.PushAgent
import org.greenrobot.eventbus.EventBus



abstract class BaseActivity : RxAppCompatActivity() {
    lateinit var viewPrinter:HiViewPrinter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();
        MyActivityManager.getActivityManager().pushActivity(this)
        "${this.javaClass.simpleName}:  onCreate".log()

        immerbar()
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this)
        }
        setContentView(getLayoutId())
        initViewBefore()
        initView()
        initData()
        viewPrinter = HiViewPrinter(this)
        HiLogManager.getInstance().addPrinter(viewPrinter)
    }

    var immersionBar:ImmersionBar?=null
    private fun immerbar() {
        immersionBar = ImmersionBar.with(this)
        immersionBar?.init() //必须调用方可应用以上所配置的参数
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData()
    open fun initViewBefore() {}

    open fun changNormalTopView(context: Context, topView: View) {
        if (context == null || topView == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
       //region 地址

       //endregion
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
        //endregion
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
                .setDemines(.3f)
                .show()
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
//
        HiLogManager.getInstance().removePrinter(viewPrinter)
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
        MyActivityManager.getActivityManager().popActivity(this)
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