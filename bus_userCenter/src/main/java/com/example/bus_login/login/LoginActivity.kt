package com.czy.yq_wanandroid.business.login

import android.text.TextUtils
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig
import com.czy.business_base.dataSave.DataSaveProxy
import com.czy.business_base.entity.UserInfo
import com.czy.business_base.event.LoginEvent
import com.czy.business_base.ext.toast
import com.czy.business_base.mvpbase.MvpActivity
import com.czy.lib_base.utils.BarUtils
import com.czy.lib_log.HiLog
import com.example.bus_login.R
import com.example.bus_login.UserManage
import com.example.lib_imageloader.image.ImageLoaderUtil
import com.example.lib_imageloader.image.listener.ProgressListener
import com.google.gson.Gson
import com.infoholdcity.basearchitecture.self_extends.log
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.auth.AuthService
import com.netease.nimlib.sdk.auth.LoginInfo
import kotlinx.android.synthetic.main.activity_login.*


@Route(path = ArouterConfig.login)
class LoginActivity : MvpActivity<LoginPresenter>(), ILoginView {
    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }


    override fun initView() {
        etName.setText("yangqing1121")
        etPsw.setText("yangqing971121")
        BarUtils.setNavBarLightMode(this, false)
        btnLogin.setOnClickListener {
            var name = etName.text?.trim().toString()
            var psw = etPsw.text?.trim().toString()
            mPresenter?.login(name, psw)
        }
    }

    override fun initData() {
        btnTestIv.setOnClickListener {
            ImageLoaderUtil.getInstance().clearImageAllCache(LoginActivity@ this)
            val url =
                "https://f.sinaimg.cn/tech/transform/318/w204h114/20210203/b3a0-kirmait4650058.gif"
            ImageLoaderUtil.getInstance()
                .loadImageWithProgress(url, ivTest, object :
                    ProgressListener {
                    override fun onLoadFailed() {
                        "onLoadFailed".log()
                    }

                    override fun onLoadProgress(isDone: Boolean, progress: Int) {
                        "progress: $progress".log()
                        runOnUiThread {
                            btnTestIv.setText(progress.toString())
                        }

                    }
                })
        }
    }

    override fun loginSuccess(userInfo: UserInfo) {
        UserManage.setUserInfo(userInfo)
        LoginEvent(true).notifyEvent()
        val targetPath = intent.getStringExtra("targetPath")//登录后需要跳转的目标页面
        if (!TextUtils.isEmpty(targetPath)) {
            ARouter.getInstance().build(targetPath).navigation()
        } else {
            setResult(RESULT_OK)
        }
       finish()

    }

    override fun loginFailed(msg: String) {
        toast(msg)
    }
}