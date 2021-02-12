package com.example.lib_imageloader.image

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper
import com.github.chrisbanes.photoview.PhotoView

class MyViewDragHelper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    override fun onFinishInflate() {
        super.onFinishInflate()
    }


    init {
        ViewDragHelper.create(this,1f,object :ViewDragHelper.Callback(){
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return child is PhotoView
            }


            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return top

            }
        })
    }


}