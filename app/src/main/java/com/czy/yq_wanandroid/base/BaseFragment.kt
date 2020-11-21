package com.czy.yq_wanandroid.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.czy.yq_wanandroid.common.LoadingDialog
import com.czy.yq_wanandroid.utils.display.DisplayInfoUtils
import com.trello.rxlifecycle4.components.support.RxFragment
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment : RxFragment() {
    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData()
    open fun initViewBefore() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this)
        }
        initViewBefore()
        initView()
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = LayoutInflater.from(context).inflate(getLayoutId(), container, false)
        return rootView
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    open fun changNormalTopView(context: Context?, topView: View?) {
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
            loadingDialog = LoadingDialog.Builder(context!!)
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

    override fun onDestroyView() {
        hideLoading()
        super.onDestroyView()
    }
}