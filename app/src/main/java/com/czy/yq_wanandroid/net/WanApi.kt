package com.czy.yq_wanandroid.net

import com.czy.yq_wanandroid.entity.WxArticle
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET

interface WanApi {
    @GET("/wxarticle/chapters/json  ")
    fun getWxarticle(): Call<BaseResult<List<WxArticle>>>


    @GET("/wxarticle/chapters/json  ")
    fun getWxarticle2(): Call<String>


    @GET("/wxarticle/chapters/json  ")
    fun getWxarticle3(): Observable<String>


    @GET("/wxarticle/chapters/json  ")
    fun getWxarticle4(): Observable<BaseResult<List<WxArticle>>>


}