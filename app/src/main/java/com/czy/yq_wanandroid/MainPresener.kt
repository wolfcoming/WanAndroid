package com.czy.yq_wanandroid

import com.czy.yq_wanandroid.mvpbase.MvpPresenter
import java.util.logging.Handler

class MainPresener : MvpPresenter<IMainView>() {
    fun getMainData() {
        android.os.Handler().postDelayed({
            baseView?.showMainView()
        }, 1000)
    }
}