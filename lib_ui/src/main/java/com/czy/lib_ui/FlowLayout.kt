package com.czy.lib_ui

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView

class FlowLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    //水平间距
    private val mHorizontalSpacing = dp2px(16)

    //垂直间距
    private val mVerticalSpacing = dp2px(8)

    var flowLayoutHeight = 0
    var flowLayoutWidth = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var selfWidht = MeasureSpec.getSize(widthMeasureSpec)
        flowLayoutHeight = 0
        var curLayoutWidth = paddingLeft
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            val chirldWidthMeasureSpec = getChildMeasureSpec(
                widthMeasureSpec,
                paddingLeft + paddingRight,
                view.layoutParams.width
            )
            val chirldHeightMeasureSpec = getChildMeasureSpec(
                heightMeasureSpec,
                paddingTop + paddingBottom,
                view.layoutParams.height
            )
            view.measure(chirldWidthMeasureSpec, chirldHeightMeasureSpec)
            if (view.measuredWidth + curLayoutWidth + mHorizontalSpacing + paddingRight >= selfWidht) {
                curLayoutWidth = paddingLeft + view.measuredWidth + mHorizontalSpacing
                flowLayoutHeight = flowLayoutHeight + view.measuredHeight + mVerticalSpacing
            } else {
                curLayoutWidth += view.measuredWidth + mHorizontalSpacing
                flowLayoutHeight = Math.max(view.measuredHeight, flowLayoutHeight)
            }

        }
        setMeasuredDimension(selfWidht, flowLayoutHeight + paddingTop + paddingBottom)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var curX = paddingLeft
        var curY = paddingTop
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (curX + view.measuredWidth + mHorizontalSpacing > measuredWidth) {
                curX = paddingLeft
                curY += view.measuredHeight + mVerticalSpacing
            }
            var left = curX
            var top = curY
            var right = left + view.measuredWidth
            var bottom = top + view.measuredHeight
            view.layout(left, top, right, bottom)
            curX += view.measuredWidth + mHorizontalSpacing
        }
    }

    var datas = ArrayList<String>()

    fun addData(data: ArrayList<String>, mItemClick: (postion: Int) -> Unit) {
        this.mItemClick = mItemClick
        this.datas.clear()
        removeAllViews()
        this.datas.addAll(data)
        for (index in 0 until this.datas.size) {
            val tv = TextView(context)
            tv.setPadding(20, 10, 20, 10)
            tv.text = this.datas[index].replace(" ", "")
            tv.setBackgroundResource(R.drawable.bg_flowlaout_item)
            tv.setTextColor(Color.WHITE)
            tv.setOnClickListener {
                if (this::mItemClick.isInitialized) {
                    mItemClick(index)
                }
            }
            addView(tv, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        }
    }

    lateinit var mItemClick: (postion: Int) -> Unit


    private fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams? {
        return MarginLayoutParams(context, attrs)
    }
}