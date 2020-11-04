package com.czy.yq_wanandroid.net

import com.czy.yq_wanandroid.entity.ArticleEntity
import com.czy.yq_wanandroid.entity.ArticleList
import com.czy.yq_wanandroid.entity.Banner
import com.czy.yq_wanandroid.entity.BaseResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WanApi {


    //首页banner
    @GET("/banner/json")
    fun getHomeBanner(): Observable<BaseResult<List<Banner>>>

    //首页文章列表
    @GET("/article/list/{page}/json")
    fun getHomeArticle(@Path("page") page: Int): Observable<BaseResult<ArticleList<ArticleEntity>>>

    //首页置顶文章
    @GET("/article/top/json")
    fun getTopArticle(): Observable<BaseResult<List<ArticleEntity>>>

}