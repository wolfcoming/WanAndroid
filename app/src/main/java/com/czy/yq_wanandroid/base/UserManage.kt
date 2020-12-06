package com.czy.yq_wanandroid.base

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.czy.yq_wanandroid.business.login.LoginActivity
import com.czy.yq_wanandroid.entity.UserInfo
import com.czy.yq_wanandroid.event.LoginEvent
import com.czy.yq_wanandroid.flowResult.FlowResult

class UserManage {
    companion object {
        private var userInfo: UserInfo? = null
        fun getUserInfo(): UserInfo? {
            return userInfo
        }

        fun setUserInfo(userInfo: UserInfo) {
            this.userInfo = userInfo
        }

        fun isLogin(): Boolean {
            return userInfo != null
        }

        fun exitLogin() {
            this.userInfo = null
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
            val intent = Intent(App.mContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            App.mContext.startActivity(intent)
        }
    }
}