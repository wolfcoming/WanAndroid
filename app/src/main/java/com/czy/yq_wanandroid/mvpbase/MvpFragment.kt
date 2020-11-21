package com.czy.yq_wanandroid.mvpbase

import com.czy.yq_wanandroid.base.BaseFragment
import com.trello.rxlifecycle4.LifecycleTransformer
import com.trello.rxlifecycle4.android.FragmentEvent
import com.yangqing.record.ext.toast

abstract class MvpFragment<P : MvpPresenter<*>> : BaseFragment(), IView {

    var mPresenter: P? = null

    abstract fun createPresenter(): P
    override fun onDestroy() {
        mPresenter?.detach()
        super.onDestroy()
    }

    override fun showLoading() {
        super.showLoading()
    }

    override fun hideLoading() {
        super.hideLoading()
    }

    override fun initViewBefore() {
        super.initViewBefore()
        if (mPresenter == null) mPresenter = createPresenter()
        mPresenter?.attach(this)
    }


    override fun <T> bindLifecycleEvent(): LifecycleTransformer<T> {
//        "FragmentEvent.DESTROY 执行解除绑定".log()
        return bindUntilEvent(FragmentEvent.DESTROY)
    }

    override fun showToast(strig: String?) {
        if (strig != null) {
            toast(strig)
        }
    }


}