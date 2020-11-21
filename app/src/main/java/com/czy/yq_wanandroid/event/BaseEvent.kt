package com.czy.yq_wanandroid.event

import org.greenrobot.eventbus.EventBus

open class BaseEvent {
    fun notifyEvent() {
        EventBus.getDefault().post(this)
    }
}