package com.czy.business_base

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.czy.lib_base.utils.display.DisplayInfoUtils
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.OnBarListener
import com.gyf.immersionbar.OnKeyboardListener
import com.infoholdcity.basearchitecture.self_extends.log
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus


abstract class BaseActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "${this.javaClass.simpleName}:  onCreate".log()
//        //去除状态栏区域，让contentView能够展示在状态栏里边
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.decorView.systemUiVisibility =
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = Color.TRANSPARENT
//        }
        immerbar()


        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this)
        }
        setContentView(getLayoutId())
        initViewBefore()
        initView()
        initData()
    }

    var immersionBar:ImmersionBar?=null
    private fun immerbar() {
        immersionBar = ImmersionBar.with(this)
//            .transparentStatusBar() //透明状态栏，不写默认透明色
//            .transparentNavigationBar() //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
//            .transparentBar() //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
//            .statusBarColor(R.color.colorPrimary) //状态栏颜色，不写默认透明色
//            .navigationBarColor(R.color.colorPrimary) //导航栏颜色，不写默认黑色
//            .barColor(R.color.colorPrimary) //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
//            .statusBarAlpha(0.3f) //状态栏透明度，不写默认0.0f
//            .navigationBarAlpha(0.4f) //导航栏透明度，不写默认0.0F
//            .barAlpha(0.3f) //状态栏和导航栏透明度，不写默认0.0f
//            .statusBarDarkFont(true) //状态栏字体是深色，不写默认为亮色
//            .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
//            .autoDarkModeEnable(true) //自动状态栏字体和导航栏图标变色，必须指定状态栏颜色和导航栏颜色才可以自动变色哦
//            .autoStatusBarDarkModeEnable(true, 0.2f) //自动状态栏字体变色，必须指定状态栏颜色才可以自动变色哦
//            .autoNavigationBarDarkModeEnable(true, 0.2f) //自动导航栏图标变色，必须指定导航栏颜色才可以自动变色哦
//            .flymeOSStatusBarFontColor(R.color.btn3) //修改flyme OS状态栏字体颜色
//            .fullScreen(true) //有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
//            .hideBar(BarHide.FLAG_HIDE_BAR) //隐藏状态栏或导航栏或两者，不写默认不隐藏
//            .addViewSupportTransformColor(toolbar) //设置支持view变色，可以添加多个view，不指定颜色，默认和状态栏同色，还有两个重载方法
//            .titleBar(view) //解决状态栏和布局重叠问题，任选其一
//            .titleBarMarginTop(view) //解决状态栏和布局重叠问题，任选其一
//            .statusBarView(view) //解决状态栏和布局重叠问题，任选其一
//            .fitsSystemWindows(true) //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
//            .supportActionBar(true) //支持ActionBar使用
//            .statusBarColorTransform(R.color.orange) //状态栏变色后的颜色
//            .navigationBarColorTransform(R.color.orange) //导航栏变色后的颜色
//            .barColorTransform(R.color.orange) //状态栏和导航栏变色后的颜色
//            .removeSupportView(toolbar) //移除指定view支持
//            .removeSupportAllView() //移除全部view支持
//            .navigationBarEnable(true) //是否可以修改导航栏颜色，默认为true
//            .navigationBarWithKitkatEnable(true) //是否可以修改安卓4.4和emui3.x手机导航栏颜色，默认为true
//            .navigationBarWithEMUI3Enable(true) //是否可以修改emui3.x手机导航栏颜色，默认为true
//            .keyboardEnable(true) //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
//            .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE) //单独指定软键盘模式
//            .setOnKeyboardListener(OnKeyboardListener { isPopup, keyboardHeight ->
//
//                //软键盘监听回调，keyboardEnable为true才会回调此方法
//                LogUtils.e(isPopup) //isPopup为true，软键盘弹出，为false，软键盘关闭
//            })
//            .setOnNavigationBarListener(onNavigationBarListener) //导航栏显示隐藏监听，目前只支持华为和小米手机
//            .setOnBarListener(OnBarListener) //第一次调用和横竖屏切换都会触发，可以用来做刘海屏遮挡布局控件的问题
//            .addTag("tag") //给以上设置的参数打标记
//            .getTag("tag") //根据tag获得沉浸式参数
//            .reset() //重置所以沉浸式参数
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
                .setDemines(0f)
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