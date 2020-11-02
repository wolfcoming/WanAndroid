package com.czy.yq_wanandroid.net

import android.util.Log
import com.czy.yq_wanandroid.net.convert.CustomGsonConverterFactory
import com.czy.yq_wanandroid.net.interceptor.NetInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit

class WanApiService {
    companion object {
        private val timeout: Long = 8 * 1000
        private val baseurl = "https://www.wanandroid.com"
        private var wanApi: WanApi? = null

        private val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(NetInterceptor())
            .build()

        fun getWanApi(): WanApi {
            if (wanApi == null) {
                Log.e("YYYYY", "getWanApi: 初始化")
                val mRetrofit = Retrofit.Builder()
                    .baseUrl(baseurl)
                    .addConverterFactory(CustomGsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build()
                wanApi = mRetrofit.create(WanApi::class.java)
            }
            return wanApi!!
        }
    }
}