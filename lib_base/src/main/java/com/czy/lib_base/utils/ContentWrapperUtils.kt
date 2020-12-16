package com.czy.lib_base.utils

import android.content.Context

object ContentWrapperUtils {
    lateinit var mContext: Context
    fun init(context: Context) {
        mContext = context
    }

}