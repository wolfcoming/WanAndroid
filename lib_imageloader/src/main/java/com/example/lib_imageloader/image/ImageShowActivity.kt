package com.example.lib_imageloader.image

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.lib_imageloader.R
import kotlinx.android.synthetic.main.activity_image_show.*

class ImageShowActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBar()

        setContentView(R.layout.activity_image_show)
        overridePendingTransition(R.anim.fade_alpha_in,R.anim.fade_alpha_out)
        val imageUrl = intent.getStringExtra("url")
        showImage(imageUrl)

        ivShow.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_alpha_in,R.anim.fade_alpha_out)
        }
    }


    private fun hideSystemBar() {
        //去除状态栏区域，让contentView能够展示在状态栏里边
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)//将内容绘制到systembar后边
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
    }


    private fun showImage(imageUrl: String?) {
        ImageLoaderUtil.getInstance().loadImage(imageUrl, ivShow)
    }
}