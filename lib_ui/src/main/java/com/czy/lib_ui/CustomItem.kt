package com.czy.lib_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.czy.lib_ui.R

class CustomItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.custom_item_layout, this, false)
        addView(itemView)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }
}