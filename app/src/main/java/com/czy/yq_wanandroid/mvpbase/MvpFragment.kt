package com.czy.yq_wanandroid.mvpbase

import com.czy.yq_wanandroid.base.BaseFragment
import com.infoholdcity.basearchitecture.self_extends.log
import com.trello.rxlifecycle4.LifecycleTransformer
import com.trello.rxlifecycle4.android.FragmentEvent

abstract class MvpFragment<P : MvpPresenter<*>> : BaseFragment(), IView {

    var mPresenter: P? = null

    abstract fun createPresenter(): P
    override fun onDestroy() {
        mPresenter?.detach()
        super.onDestroy()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun initViewBefore() {
        super.initViewBefore()
        if (mPresenter == null) mPresenter = createPresenter()
        mPresenter?.attach(this)
    }


    override fun <T> bindLifecycleEvent(): LifecycleTransformer<T> {
        "FragmentEvent.DESTROY 执行解除绑定".log()
        return bindUntilEvent(FragmentEvent.DESTROY)
    }


}