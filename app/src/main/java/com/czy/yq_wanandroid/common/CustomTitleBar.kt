package com.czy.yq_wanandroid.common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.czy.yq_wanandroid.R
import kotlinx.android.synthetic.main.common_title.view.*

class CustomTitleBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        val titlView = LayoutInflater.from(context).inflate(R.layout.common_title, null)
        addView(
            titlView, LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        gravity = Gravity.CENTER
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar)
        val leftType = typeArray.getString(R.styleable.CustomTitleBar_leftType)
        if (leftType.equals("0")) {
            val leftValue = typeArray.getResourceId(R.styleable.CustomTitleBar_left, 0)
            btnLeft.setText("")
            btnLeft.setBackgroundResource(leftValue)
        } else {
            val leftValue = typeArray.getString(R.styleable.CustomTitleBar_left)
            btnLeft.setText(leftValue)
        }


        val rightType = typeArray.getString(R.styleable.CustomTitleBar_rightType)
        if (rightType.equals("0")) {
            val rightValue = typeArray.getResourceId(R.styleable.CustomTitleBar_right, 0)
            btnRight.text = ""
            btnRight.setBackgroundResource(rightValue)
        } else {
            val rightValue = typeArray.getString(R.styleable.CustomTitleBar_right)
            btnRight.text = rightValue
        }


        val title = typeArray.getString(R.styleable.CustomTitleBar_title)
        setTitle(title)
        typeArray.recycle()
        btnLeft.setOnClickListener {
            if (this::leftClick.isInitialized) {
                leftClick(it)
            }
        }
        btnRight.setOnClickListener {
            if (this::rightClick.isInitialized) {
                rightClick(it)
            }
        }
    }


    private lateinit var leftClick: (v: View) -> Unit
    private lateinit var rightClick: (v: View) -> Unit

    fun leftClickListener(click: (v: View) -> Unit) {
        this.leftClick = click
    }

    fun rightClickListener(click: (v: View) -> Unit) {
        this.rightClick = click
    }

    fun setTitle(title: String?) {
        title?.let {
            tvTitle.text = it
        }
    }
}