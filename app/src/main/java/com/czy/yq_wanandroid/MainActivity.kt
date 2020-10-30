package com.czy.yq_wanandroid

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.czy.yq_wanandroid.entity.WxArticle
import com.czy.yq_wanandroid.mvpbase.MvpActivity
import com.czy.yq_wanandroid.net.BaseResult
import com.czy.yq_wanandroid.net.WanApiService
import com.czy.yq_wanandroid.proxyLean.Man
import com.czy.yq_wanandroid.proxyLean.MyProxy
import com.czy.yq_wanandroid.proxyLean.Subject
import com.infoholdcity.basearchitecture.self_extends.log
import com.yangqing.record.ext.toast
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.lang.reflect.Proxy

class MainActivity : MvpActivity<MainPresener>(),IMainView {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter(): MainPresener {
        return MainPresener()
    }

    override fun init() {
        mPresenter?.getMainData()
    }

    override fun showMainView() {
        toast("showMainView")
    }

    override fun getContent(): Context {
         return this
    }
}