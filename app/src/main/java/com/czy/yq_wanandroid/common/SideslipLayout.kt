package com.czy.yq_wanandroid.common

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.Scroller
import com.infoholdcity.basearchitecture.self_extends.log
import kotlin.math.abs

open class SideslipLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    companion object {
        var sideslipLayout: SideslipLayout? = null
    }

    lateinit var menuView: View
    var criticalValue = 0//临界值 滑动超过这个值自动打开，否则关闭


    var scroller = Scroller(context)
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                var dexX = ev.x - downX
                var dexY = ev.y - downY
                if (abs(dexX) > abs(dexY)) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        var menuView = getChildAt(1)
        criticalValue = menuView.measuredWidth / 2
        criticalValue.log()
    }

    var isClickEvent = true
    var downX = 0f
    var downY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isClickEvent = true
                if (sideslipLayout != null && sideslipLayout != this) {
                    if (sideslipLayout!!.isOpenStatus()) {
                        requestDisallowInterceptTouchEvent(true)
                        sideslipLayout?.smoothClose()
                        requestDisallowInterceptTouchEvent(false)
                    }
                }
                drawableHotspotChanged(event.x, event.y)
                setPressed(true)
            }

            MotionEvent.ACTION_MOVE -> {
                isClickEvent = false
                var dexX = event.x - downX
                var dexY = event.y - downY
                if (abs(dexX) > abs(dexY)) {
                    //移动当前viewgroup的位置
                    requestDisallowInterceptTouchEvent(true)
                    scrollBy(-dexX.toInt(), 0)
                    setPressed(false)
                    limitScroll()
                }
            }
            MotionEvent.ACTION_UP -> {
                //手势抬起时 自动滚动到合适位置
                scrollToSuitablePosition()
                "ACTION_UP".log()
                requestDisallowInterceptTouchEvent(false)
                if (isClickEvent) {
                    performClick()
                    setPressed(false)
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                setPressed(false)
            }
        }
        downY = event.y
        downX = event.x
        return true
    }

    /**
     * 滚动到合适位置
     */
    private fun scrollToSuitablePosition() {
        if (scrollX > criticalValue) {
            smoothExpand()
        } else {
            smoothClose()
        }
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller.computeScrollOffset()) {
            this.scrollTo(scroller.currX, 0)
            invalidate()
        }
    }

    private fun limitScroll() {
        //限制滑动范围
        if (scrollX > criticalValue * 2) scrollTo(criticalValue * 2, 0)
        if (scrollX < 0) scrollTo(0, 0)
    }

    fun isOpenStatus(): Boolean {
        return scrollX != 0
    }


    private fun smoothExpand() {
        sideslipLayout = this
        scroller.startScroll(scrollX, 0, criticalValue * 2 - scrollX, 0)
        invalidate()
    }

    private fun smoothClose() {
        scroller.startScroll(scrollX, 0, -scrollX, 0)
        invalidate()
    }

    fun close(){
        scrollTo(0,0)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        sideslipLayout = null
    }
}