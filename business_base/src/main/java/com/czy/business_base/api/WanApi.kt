package com.czy.business_base.api

import com.czy.business_base.entity.*
import com.czy.business_base.net.entity.BaseResult
import com.czy.lib_net.annotation.BaseUrl
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

@BaseUrl("https://www.wanandroid.com")
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

    //体系数据下的文章列表
    @GET("/article/list/{page}/json")
    fun getProjectsListById(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Observable<BaseResult<ArticleList<ArticleEntity>>>


    //登录
    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<BaseResult<UserInfo>>


    /**
     * 收藏文章列表
     * 方法：GET
     * 参数： 页码：拼接在链接中，从0开始。
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectArticleList(@Path("page") page: Int): Observable<BaseResult<ArticleList<ArticleEntity>>>

    /**
     * 收藏文章
     */
    @POST("lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Observable<BaseResult<String>>

    /**
     * 取消收藏文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun unCollectArticle(@Path("id") id: Int): Observable<BaseResult<String>>

    //搜索热词
    @GET("hotkey/json")
    fun getHotKeys(): Observable<BaseResult<List<HotKey>>>

    //搜索
    @FormUrlEncoded
    @POST("article/query/{id}/json")
    fun search(
        @Path("id") id: Int,
        @Field("k") words: String
    ): Observable<BaseResult<ArticleList<ArticleEntity>>>

}