package com.czy.yq_wanandroid.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.czy.yq_wanandroid.R
import okhttp3.*
import java.io.IOException

class OkHttpSoureLeanrActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.okhttp_learn)
        val okHttpClient = OkHttpClient.Builder().build()
        val request = Request.Builder().url("url").get().build()
        val call: Call = okHttpClient.newCall(request)
        val execute = call.execute();
        val result = execute.body?.string()
        call.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {

            }
        })
    }
}