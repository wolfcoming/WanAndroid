package com.czy.business_base

object ArouterConfig {
    const val webviewPath: String = "/webview/web_activity"

    //app模块
    const val mainActivity: String = "/app/mainActivity"

    //登录模块
    const val login: String = "/userCenter/loginActivity"

    //收藏页面
    const val collectPage = "/userCenter/collectPage"


    //拦截器--登录拦截
    const val intercept_login = 1

    //降级界面
    const val lost_page = "/lost/lost_page"

    //网络请求错误码处理
    const val errorCodeProcess: String = "/intercept/errorCode"
}