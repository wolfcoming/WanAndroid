package com.czy.yq_wanandroid.business.home.mine

import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig
import com.czy.business_base.LazyFragment
import com.czy.business_base.entity.UserInfo
import com.czy.business_base.event.LoginEvent
import com.czy.business_base.service.ServiceFactory
import com.czy.lib_log.HiLog
import com.czy.yq_wanandroid.R
import com.czy.yq_wanandroid.business.SettingActivity
import com.czy.yq_wanandroid.business.readHistory.ReadHistoryActivity
import kotlinx.android.synthetic.main.fragment_mine.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MineFragment : LazyFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        tv_user_name.setOnClickListener {
            if (!ServiceFactory.getUserService().isLogin()) {
                ARouter.getInstance().build(ArouterConfig.login).navigation()
            }
        }

        ll_collect.setOnClickListener {
            //换成拦截器来主动拦截
//            ServiceFactory.getUserService().checkLogin(context!!) {
//                  ARouter.getInstance().build(ArouterConfig.collectPage).navigation()
//            }
            ARouter.getInstance().build(ArouterConfig.collectPage).navigation()
        }
        ll_history.setOnClickListener {
            startActivity(Intent(context, ReadHistoryActivity::class.java))
        }
        ll_exit.setOnClickListener {
            ServiceFactory.getUserService().exitLogin()
        }
        ll_system.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
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
        if (ServiceFactory.getUserService().isLogin()) {
            showUserInfo(ServiceFactory.getUserService().getUserInfo())
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