package com.czy.yq_wanandroid

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.czy.yq_wanandroid.entity.WxArticle
import com.czy.yq_wanandroid.net.BaseResult
import com.czy.yq_wanandroid.net.WanApiService
import com.yangqing.record.ext.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         findViewById<Button>(R.id.btnTest).setOnClickListener {
             WanApiService.getWanApi().getWxarticle().enqueue(object :
                 Callback<BaseResult<List<WxArticle>>> {
                 override fun onResponse(
                     call: Call<BaseResult<List<WxArticle>>>,
                     response: Response<BaseResult<List<WxArticle>>>) {
                     val wxArticle = response.body()?.data?.get(0)!!
                     Log.e("YYYYY", "onCreate: " + wxArticle.name)
                 }

                 override fun onFailure(call: Call<BaseResult<List<WxArticle>>>, t: Throwable) {

                 }
             })
         }
        findViewById<Button>(R.id.btnTest2).setOnClickListener() {

            WanApiService.getWanApi().getWxarticle2().enqueue(object :
                Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>) {
                    val wxArticle = response.body()
                    Log.e("YYYYY", "onCreate: " + wxArticle)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("YYYYY", "onCreate: " + t.printStackTrace())
                }
            })
        }
    }
}