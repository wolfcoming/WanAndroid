package com.czy.business_base

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.czy.lib_ui.RotatingRing

class LoadingDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    lateinit var rotatingRing: RotatingRing
    fun setBuilder(builder: Builder) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null)
        setContentView(view)
        val tvMessage = view.findViewById<TextView>(R.id.tv_msg)
        rotatingRing = view.findViewById<RotatingRing>(R.id.pb)
        tvMessage.text = builder.mMsg
        setCancelable(builder.mCancle)
        if (builder.dimens == 0f) {
            //解决 对话框引起的状态栏文字颜色变白问题
            window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        window?.setDimAmount(builder.dimens)
    }

    override fun show() {
        rotatingRing.startAnimal()
        super.show()
    }

    override fun dismiss() {
        rotatingRing.stopAnimal()
        super.dismiss()
    }

    open class Builder(context: Context) {
        var mCancle: Boolean = true
        var mMsg: String = "加载中。。"
        var mContext = context
        var dimens: Float = 0.4f

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

        fun show(): LoadingDialog {
            val create = create()
            create.show()
            return create
        }

        fun create(): LoadingDialog {
            val loadingDialog = LoadingDialog(mContext, R.style.myDialog_with_dimAmount)
            loadingDialog.setBuilder(this)
            return loadingDialog
        }
    }
}