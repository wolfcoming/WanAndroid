package com.czy.yq_wanandroid.test

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.infoholdcity.basearchitecture.self_extends.log

class CustomLineLayoutC @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        "C(${ev?.action}):  dispatchTouchEvent".log()
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        "C(${event?.action}):  onTouchEvent".log()
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        "C(${ev?.action}):  onInterceptTouchEvent".log()
        return super.onInterceptTouchEvent(ev)
    }
}