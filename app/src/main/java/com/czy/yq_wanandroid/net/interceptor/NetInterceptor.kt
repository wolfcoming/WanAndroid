package com.czy.yq_wanandroid.net.interceptor

import com.czy.yq_wanandroid.entity.BaseResult
import com.google.gson.Gson
import com.infoholdcity.basearchitecture.self_extends.log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset


class NetInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val UTF8 = Charset.forName("UTF-8")
        val response = chain.proceed(chain.request())
        val body = response.body
        if (body != null) {
            try {
                val responseBody = getResponseBody(body)
                val result = Gson().fromJson<BaseResult<*>>(responseBody, BaseResult::class.java)
                val url = chain.request().url
                val headers = chain.request().headers
                val requestBody = chain.request().body
                var body: String = ""
                requestBody?.let {
                    val buffer = Buffer()
                    it.writeTo(buffer)
                    var charset: Charset = UTF8
                    val contentType = it.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(UTF8)!!
                    }
                    body = buffer.readString(charset);
                }

                ("\" \n============okhttp拦截器打印============================\"" +
                        "\n 请求url：$url \n 请求头:\n ${headers.toString()} \n " +
                        "请求参数：$body \n" +
                        "请求结果：\n $result" +
                        "\"\n ============okhttp拦截器打印============================\"").log()
            } catch (e: Exception) {
            }
        }

        return response
    }

    @Throws(IOException::class)
    fun getResponseBody(responseBody: ResponseBody): String {
        val source = responseBody.source()
        // 获取全部body的数据
        source.request(Long.MAX_VALUE)
        val buffer: Buffer = source.buffer
        // 在读取缓存去之前clone数据，解决response.body().string()只能读取一次的问题
        return buffer.clone().readString(Charset.forName("UTF-8"))
    }
}