package com.czy.yq_wanandroid.event

/**
 * 用户登录后的通知事件
 */
class LoginEvent : BaseEvent {
    var isLogin: Boolean = false

    constructor(login: Boolean) {
        this.isLogin = login
    }
}