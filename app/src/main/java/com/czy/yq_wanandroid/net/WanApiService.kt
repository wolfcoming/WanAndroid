package com.czy.yq_wanandroid.net

import com.czy.lib_base.net.convert.CustomGsonConverterFactory
import com.czy.lib_base.net.interceptor.NetInterceptor
import com.infoholdcity.basearchitecture.self_extends.log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
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
            .cookieJar(
//                PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.mContext))
                CookieManage()
            )
//            .cache(Cache(App.mContext.cacheDir, 10 * 1024 * 1024))
            .build()

        fun getWanApi(): WanApi {
            if (wanApi == null) {
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

    class CookieManage : CookieJar {
        var cache: MutableList<Cookie> = ArrayList()
        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            //过期的Cookie
            val invalidCookies: MutableList<Cookie> = ArrayList()
            //有效的Cookie
            val validCookies: MutableList<Cookie> = ArrayList()
            for (cookie in cache) {
                if (cookie.expiresAt < System.currentTimeMillis()) {
                    //判断是否过期
                    invalidCookies.add(cookie)
                } else if (cookie.matches(url)) {
                    //匹配Cookie对应url
                    validCookies.add(cookie)
                }
            }
            //缓存中移除过期的Cookie
            cache.removeAll(invalidCookies)
            //返回List<Cookie>让Request进行设置
            "上传的cookie：$validCookies".log()
            return validCookies
        }

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            "获取到的：cookie $cookies".log()
            cache.addAll(cookies);
        }

    }
}