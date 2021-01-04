package com.czy.business_base.event

import org.greenrobot.eventbus.EventBus

open class BaseEvent {
    fun notifyEvent() {
        EventBus.getDefault().post(this)
    }
}