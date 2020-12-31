package com.czy.yq_wanandroid.business.login

import com.czy.lib_base.mvpbase.MvpActivity
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.base.UserManage
import com.czy.yq_wanandroid.entity.UserInfo
import com.czy.yq_wanandroid.event.LoginEvent
import com.yangqing.record.ext.toast
import kotlinx.android.synthetic.main.activity_login.*

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