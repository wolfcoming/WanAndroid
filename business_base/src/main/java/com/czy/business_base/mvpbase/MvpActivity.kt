package com.czy.business_base.mvpbase

import android.widget.Toast
import com.czy.business_base.BaseActivity
import com.trello.rxlifecycle4.LifecycleTransformer
import com.trello.rxlifecycle4.android.ActivityEvent

abstract class MvpActivity<P : MvpPresenter<*>> : BaseActivity(), IView {
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
//        "ActivityEvent.DESTROY 执行解除绑定".log()
        return bindUntilEvent(ActivityEvent.DESTROY)
    }

    override fun showToast(strig: String?) {
        if (strig != null) {
            Toast.makeText(this, strig, Toast.LENGTH_SHORT).show()
        }
    }
}