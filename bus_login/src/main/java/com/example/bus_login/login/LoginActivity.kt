package com.czy.yq_wanandroid.business.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.czy.business_base.entity.UserInfo
import com.czy.business_base.event.LoginEvent
import com.czy.business_base.ArouterConfig
import com.czy.business_base.mvpbase.MvpActivity
import com.example.bus_login.R
import com.example.bus_login.UserManage
import com.example.lib_imageloader.image.ImageLoaderUtil
import com.yangqing.record.ext.toast
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
                "https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2317977391,2413939729&fm=26&gp=0.jpg"
//            ImageLoaderUtil.getInstance().loadCircleBorderImage(url, R.drawable.icon_empty, ivTest,18f,Color.RED)


//            val url = "https://f.sinaimg.cn/tech/transform/318/w204h114/20210203/b3a0-kirmait4650058.gif"
//            ImageLoaderUtil.getInstance().loadGifImage(url, R.drawable.icon_empty, ivTest)
//            ImageLoaderUtil.getInstance().loadImageWithPrepareCall(url, ivTest, R.drawable.icon_empty, object :SourceReadyListener{
//                override fun onResourceReady(width: Int, height: Int) {
//                    "onResourceReady $width  $height".log()
//                }
//
//
//            })
            ImageLoaderUtil.getInstance().loadImageWithRadius(url,R.drawable.icon_empty, ivTest,10f)
        }
    }

    override fun loginSuccess(userInfo: UserInfo) {
        UserManage.setUserInfo(userInfo)
        setResult(RESULT_OK)
        LoginEvent(true).notifyEvent()
        finish()
    }

    override fun loginFailed(msg: String) {
        toast(msg)
    }
}