package com.example.bus_login

import android.content.Context
import android.content.Intent
import com.czy.business_base.entity.UserInfo
import com.czy.business_base.event.LoginEvent
import com.czy.business_base.flowResult.FlowResult
import com.czy.lib_base.utils.ContentWrapperUtils
import com.czy.yq_wanandroid.business.login.LoginActivity

class UserManage {
    companion object {
        private var userInfo: UserInfo? = null
        fun getUserInfo(): UserInfo? {
            return userInfo
        }

        fun setUserInfo(userInfo: UserInfo) {
            Companion.userInfo = userInfo
        }

        fun isLogin(): Boolean {
            return userInfo != null
        }

        fun exitLogin() {
            userInfo = null
            LoginEvent(false).notifyEvent()
        }

        /**
         * 检测登录状态并自动登录
         */
        fun checkLogin(context: Context, result: () -> Unit) {
            if (isLogin()) {
                result()
            } else {
                FlowResult.Builder(context)
                    .setIntent(Intent(context, LoginActivity::class.java))
                    .addResultListener { result() }
                    .call()
            }
        }

        fun gotoLogin() {
            val intent = Intent(ContentWrapperUtils.mContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            ContentWrapperUtils.mContext.startActivity(intent)
        }
    }
}