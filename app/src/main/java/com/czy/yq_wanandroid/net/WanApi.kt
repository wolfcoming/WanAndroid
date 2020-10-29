package com.czy.yq_wanandroid.net

import com.czy.yq_wanandroid.entity.WxArticle
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET

interface WanApi {
    @GET("/wxarticle/chapters/json  ")
    fun getWxarticle(): Call<BaseResult<List<WxArticle>>>


    @GET("/wxarticle/chapters/json  ")
    fun getWxarticle2(): Call<String>
}