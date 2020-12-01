package com.czy.yq_wanandroid.business.home.mine

import android.content.Intent
import android.view.View
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.base.BaseFragment
import com.czy.yq_wanandroid.base.UserManage
import com.czy.yq_wanandroid.business.collect.CollectArticleActivity
import com.czy.yq_wanandroid.business.login.LoginActivity
import com.czy.yq_wanandroid.business.readHistory.ReadHistoryActivity
import com.czy.yq_wanandroid.entity.UserInfo
import com.czy.yq_wanandroid.event.LoginEvent
import com.yangqing.record.ext.toast
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        tv_user_name.setOnClickListener {
            if (!UserManage.isLogin()) {
                startActivity(Intent(context, LoginActivity::class.java))
            }
        }

        ll_collect.setOnClickListener {
            UserManage.checkLogin(context!!) {
                startActivity(Intent(context, CollectArticleActivity::class.java))
            }
        }
        ll_history.setOnClickListener {
            startActivity(Intent(context, ReadHistoryActivity::class.java))
        }
        ll_exit.setOnClickListener {
            UserManage.exitLogin()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onLoginEvent(loginEvent: LoginEvent) {
        if (isDetached) return
        if (loginEvent.isLogin) {
            getUserInfo()
        } else {
            showUnLoginView()
        }

    }

    private fun getUserInfo() {
        if (UserManage.isLogin()) {
            showUserInfo(UserManage.getUserInfo())
        }
    }

    override fun initData() {
        getUserInfo()
    }

    private fun showUserInfo(userInfo: UserInfo?) {
        if (userInfo == null) return
        ll_exit.visibility = View.VISIBLE
        tv_user_name.text = userInfo.nickname
    }

    private fun showUnLoginView() {
        tv_user_name.text = "点击登录"
        ll_exit.visibility = View.GONE
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

}