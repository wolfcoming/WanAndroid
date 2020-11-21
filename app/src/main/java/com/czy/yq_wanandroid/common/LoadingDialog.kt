package com.czy.yq_wanandroid.common

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.czy.yq_wanandroid.R

class LoadingDialog(context: Context) : Dialog(context) {
    open class Builder(context: Context) {
        private var mCancle: Boolean = true
        private var mMsg: String = "加载中。。"
        private var mContext = context
        private var dimens: Float = 0.4f

        fun setLoadingMsg(msg: String): Builder {
            this.mMsg = msg
            return this
        }

        fun setCanCancel(cancle: Boolean): Builder {
            this.mCancle = cancle
            return this
        }

        fun setDemines(dimens: Float): Builder {
            this.dimens = dimens
            return this
        }

        fun build(): LoadingDialog {
            val loadingDialog = LoadingDialog(mContext)
            val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null)
            loadingDialog.setContentView(view)
            val tvMessage = view.findViewById<TextView>(R.id.tv_msg)
            tvMessage.text = mMsg
            loadingDialog.setCancelable(mCancle)
            loadingDialog.window?.setDimAmount(dimens)
            return loadingDialog
        }

    }
}