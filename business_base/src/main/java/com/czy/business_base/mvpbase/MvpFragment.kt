package com.czy.business_base.mvpbase

import android.widget.Toast
import com.czy.business_base.LazyFragment
import com.trello.rxlifecycle4.LifecycleTransformer
import com.trello.rxlifecycle4.android.FragmentEvent

abstract class MvpFragment<P : MvpPresenter<*>> : LazyFragment(), IView {

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
            Toast.makeText(context, strig, Toast.LENGTH_SHORT).show()
        }
    }

}