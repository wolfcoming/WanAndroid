package com.czy.yq_wanandroid.test

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.infoholdcity.basearchitecture.self_extends.log

class CustomLineLayoutA @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        "A(${ev?.action}):  dispatchTouchEvent".log()
        val dispatchTouchEvent = super.dispatchTouchEvent(ev)
//        "A(${ev?.action}) result:  dispatchTouchEvent $dispatchTouchEvent ".log()
        return dispatchTouchEvent
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        "A(${event?.action}):  onTouchEvent".log()
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        "A(${ev?.action}):  onInterceptTouchEvent".log()
        return false
    }
}