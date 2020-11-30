package com.czy.yq_wanandroid.test

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.infoholdcity.basearchitecture.self_extends.log

class CustomLineLayoutB @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        "B(${ev?.action}):  dispatchTouchEvent".log()
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        "B(${event?.action}):  onTouchEvent".log()
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        "B(${ev?.action}):  onInterceptTouchEvent".log()
        return super.onInterceptTouchEvent(ev)
    }
}