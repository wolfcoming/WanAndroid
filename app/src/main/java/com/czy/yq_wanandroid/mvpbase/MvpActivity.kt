package com.czy.yq_wanandroid.mvpbase

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

abstract class MvpActivity<P : MvpPresenter<*>> : AppCompatActivity(), IView {
    var mPresenter: P? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(getLayoutId())
        if (mPresenter == null) mPresenter = createPresenter()
        mPresenter?.attach(this)
        init()
    }

    abstract fun getLayoutId(): Int
    abstract fun createPresenter(): P
    abstract fun init()

    override fun onDestroy() {
        mPresenter?.detach()
        super.onDestroy()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }


}