package com.czy.yq_wanandroid.net

import com.czy.yq_wanandroid.entity.*
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

    //问答列表
    @GET("/wenda/list/{page}/json")
    fun getAnswerList(@Path("page") page: Int): Observable<BaseResult<ArticleList<ArticleEntity>>>

    //体系数据
    @GET("/tree/json")
    fun getProjectsData(): Observable<BaseResult<List<ProjectEntity>>>
}