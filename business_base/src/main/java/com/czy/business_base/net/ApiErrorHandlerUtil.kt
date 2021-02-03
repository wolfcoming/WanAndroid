package com.czy.business_base.net

import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig
import com.czy.business_base.Constants
import com.czy.business_base.net.entity.BaseResult
import com.czy.business_base.provider.IErrorCodeInterceptProvider
import com.czy.lib_base.utils.ContentWrapperUtils
import com.czy.lib_base.utils.NetUtils
import com.google.gson.Gson
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLException

/**
 *
 * @author yangqing
 * @time   2020/11/2 11:18 AM
 * @describe api错误统一处理类
 */
object ApiErrorHandlerUtil {

    fun throwApiException(result: String) {
        try {
            val baseResult = Gson().fromJson(result, BaseResult::class.java)
            throwApiException(baseResult)
        } catch (e: Exception) {
            throw ApiException(-1, "json 解析异常")
        }
    }

    fun <T> throwApiException(result: T) {
        if (result is BaseResult<*>) {
            if (result.errorCode != Constants.API_SUCCESS_CODE) {
                val provider: IErrorCodeInterceptProvider =
                    ARouter.getInstance().build(ArouterConfig.errorCodeProcess)
                        .navigation() as IErrorCodeInterceptProvider
                provider.processErrorCode(result.errorCode, onGetMsg(result.errorCode))
                throw ApiException(result.errorCode, result.errorMsg)
            }
        }
    }

    fun getWanApiException(t: Throwable): ApiException {
        if (t is ApiException) {
            return t
        }
        val code = onGetCode(t)
        val message = onGetMsg(code)
        return ApiException(code, message)

    }

    interface Code {
        companion object {
            const val UNKNOWN = -1
            const val NET = 0
            const val TIMEOUT = 1
            const val JSON = 2
            const val HTTP = 3
            const val HOST = 4
            const val SSL = 5
        }
    }

    /**
     * 重写该方法去返回异常对应的错误码
     *
     * @param e Throwable
     * @return 错误码
     */
    fun onGetCode(e: Throwable?): Int {
        return if (!NetUtils.isConnected(ContentWrapperUtils.mContext)) {
            Code.NET
        } else {
            if (e is SocketTimeoutException) {
                Code.TIMEOUT
            } else if (e is HttpException) {
                Code.HTTP
            } else if (e is UnknownHostException || e is ConnectException) {
                Code.HOST
            } else if (e is JsonParseException || e is ParseException || e is JSONException) {
                Code.JSON
            } else if (e is SSLException) {
                Code.SSL
            } else {
                Code.UNKNOWN
            }
        }
    }


    /**
     * @param code 错误码
     * @return 错误信息
     */
    fun onGetMsg(code: Int): String {
        return when (code) {
            Code.NET -> "网络连接失败，请检查网络设置"
            Code.TIMEOUT -> "网络状况不稳定，请稍后重试"
            Code.JSON -> "JSON解析异常"
            Code.HTTP -> "请求错误，请稍后重试"
            Code.HOST -> "服务器连接失败，请检查网络设置"
            Code.SSL -> "证书验证失败"
            Constants.API_NEED_LOGIN -> "需要登录"
            else -> "未知错误，请稍后重试"
        }
    }

}