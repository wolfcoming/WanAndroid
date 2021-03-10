package com.czy.lib_net

import com.czy.lib_net.annotation.BaseUrl
import com.czy.lib_net.annotation.TimeOut
import com.czy.lib_net.convert.CustomGsonConverterFactory
import com.infoholdcity.basearchitecture.self_extends.log
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class CommonApiService {
    companion object {
        private var TIMEOUT: Long = 8 * 1000
        private var BASEURL = ""
        var cookieManage: CookieManage? = null
        val interceptors = ArrayList<Interceptor>()
        val netInterceptors = ArrayList<Interceptor>()
        fun initOkHttp(timeout: Long = TIMEOUT): OkHttpClient {
            cookieManage = CookieManage()
            val builder = OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)

            for (interceptor in interceptors) {
                builder.addInterceptor(interceptor)
            }

            for (netInterceptor in netInterceptors) {
                builder.addNetworkInterceptor(netInterceptor)
            }

            return builder.addInterceptor(LoggingInterceptor())
                .cookieJar(cookieManage!!)
                .build()
        }

        var classHashMap = HashMap<Class<*>, Any>()
        fun <T> getRequest(api: Class<T>): T {
            val apiCreate = classHashMap.get(api)
            if (apiCreate != null) {
                return apiCreate as T
            }
            //获取api类上的注解
            var baseUrlAnno: BaseUrl? = api.getAnnotation(BaseUrl::class.java)
            if ("".equals(BASEURL)) {
                verfiyAnnotation(baseUrlAnno)
            }
            if (baseUrlAnno != null) {
                var baseUrl = baseUrlAnno!!.value
                if (!baseUrl.endsWith("/")) {
                    baseUrl += "/"
                }
                BASEURL = baseUrl
            }

            var timeOutAnno: TimeOut? = api.getAnnotation(TimeOut::class.java)
            val timeOut = timeOutAnno?.value
            if (timeOut != null) {
                TIMEOUT = timeOut
            }


            val mRetrofit = Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(initOkHttp(TIMEOUT))
                .build()

            val create = mRetrofit.create(api)
            classHashMap.put(api, create!!)
            return mRetrofit.create(api)
        }

        private fun verfiyAnnotation(annotation: BaseUrl?) {
            if (annotation == null)
                throw NullPointerException(
                    "\n" +
                            "==================================================================" +
                            "\n" +
                            "\n" +
                            "请在接口类上定义 baseUrl：参考实例：" +
                            "\n" +
                            "\n" +
                            "@BaseUrl(UrlConfig.BaseWeatherUrl)\n" +
                            "interface WeatherApi {\n" +
                            "    @GET(\"weather_mini\")\n" +
                            "    fun getWeather(@Query(\"city\") city: String): Observable<WeatherBean>\n" +
                            "}" +
                            "" +
                            "" +
                            "\n" +
                            "\n" +
                            "==================================================================" +
                            ""
                ) as Throwable
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