package com.example.lib_imageloader.image

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.lib_imageloader.R
import kotlinx.android.synthetic.main.activity_image_show.*

class ImageShowActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_image_show)
        val imageUrl = intent.getStringExtra("url")
        showImage(imageUrl)
    }

    private fun showImage(imageUrl: String?) {
        ImageLoaderUtil.getInstance().loadImage(imageUrl,ivShow)
    }
}