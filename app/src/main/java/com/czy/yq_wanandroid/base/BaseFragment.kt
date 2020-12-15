package com.czy.yq_wanandroid.base

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.czy.yq_wanandroid.common.LoadingDialog
import com.czy.yq_wanandroid.utils.display.DisplayInfoUtils
import com.infoholdcity.basearchitecture.self_extends.log
import com.trello.rxlifecycle4.components.support.RxFragment
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment : RxFragment() {
    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData()
    open fun initViewBefore() {}
    var mViewCreated = false;

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = LayoutInflater.from(context).inflate(getLayoutId(), container, false)
        mViewCreated = true
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
        return rootView
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isRegisterEventBus()) {
            if(!EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().register(this)
            }
        }
        initViewBefore()
        initView()
        initData()
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
    }


    override fun onStart() {
        super.onStart()
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
    }

    override fun onResume() {
        super.onResume()
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
    }

    override fun onPause() {
        super.onPause()
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
    }

    override fun onStop() {
        super.onStop()
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
    }


    override fun onDestroyView() {
        hideLoading()
        super.onDestroyView()
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
    }



    override fun onDestroy() {
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
        
    }

    override fun onDetach() {
        super.onDetach()
        "${this::class.java.simpleName} ${Thread.currentThread()
            .getStackTrace()[2].getMethodName()}".log("FFFFFFF")
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


}