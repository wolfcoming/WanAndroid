package com.czy.yq_wanandroid.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.czy.yq_wanandroid.R

class MultipleViewLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    lateinit var emptyView: ViewGroup
    var loadingView: ViewGroup
    var errorView: ViewGroup
    lateinit var contentView: ViewGroup
    lateinit var tvError:TextView

    init {

        emptyView = LayoutInflater.from(context)
            .inflate(R.layout.multiple_emptyview, this, false) as ViewGroup
        loadingView = LayoutInflater.from(context)
            .inflate(R.layout.multiple_loadingview, this, false) as ViewGroup
        errorView = LayoutInflater.from(context)
            .inflate(R.layout.multiple_errorview, this, false) as ViewGroup


    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1) throw  IllegalArgumentException("MultipleView can only have one child")
        contentView = getChildAt(0) as ViewGroup
        addView(emptyView, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
        addView(loadingView, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
        addView(errorView, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))
        showLoadingView()
        tvError = errorView.findViewById<TextView>(R.id.tv_error)
        errorView.setOnClickListener {
            if(this::errorClick.isInitialized){
                errorClick()
            }
        }

        emptyView.setOnClickListener {
            if(this::emptyClick.isInitialized){
                emptyClick()
            }
        }
    }

    fun showContentView() {
        emptyView.visibility = GONE
        loadingView.visibility = GONE
        errorView.visibility = GONE
        contentView.visibility = VISIBLE
    }

    fun showLoadingView() {
        emptyView.visibility = GONE
        errorView.visibility = GONE
        contentView.visibility = GONE
        loadingView.visibility = VISIBLE
    }

    fun showEmptyView() {
        emptyView.visibility = VISIBLE
        errorView.visibility = GONE
        contentView.visibility = GONE
        loadingView.visibility = GONE
    }

    fun showErrorView(msg:String ="加载失败，点击重试") {
        emptyView.visibility = GONE
        errorView.visibility = VISIBLE
        tvError.text = msg
        contentView.visibility = GONE
        loadingView.visibility = GONE
    }

    fun setErrorViewClickListener(errorClick: () -> Unit):MultipleViewLayout {
        this.errorClick = errorClick
        return this
    }
    fun setEmptyViewClickListener(emptyClick: () -> Unit):MultipleViewLayout {
        this.emptyClick = emptyClick
        return this
    }


    lateinit var errorClick: () -> Unit
    lateinit var emptyClick: () -> Unit
}