package com.czy.yq_wanandroid.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.czy.yq_wanandroid.utils.display.DisplayInfoUtils
import com.infoholdcity.basearchitecture.self_extends.log
import com.trello.rxlifecycle4.components.support.RxFragment

abstract class BaseFragment : RxFragment() {
    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData()
    open fun initViewBefore() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        javaClass.simpleName + ":onCreate".log()
    }

    override fun onDestroy() {
        super.onDestroy()
        javaClass.simpleName + ":onDestroy".log()
    }
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
}