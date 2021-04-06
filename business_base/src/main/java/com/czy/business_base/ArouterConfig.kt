package com.czy.business_base

object ArouterConfig {
    const val webviewPath: String = "/webview/web_activity"

    //app模块
    const val mainActivity: String = "/app/mainActivity"

    //登录模块
    const val login: String = "/userCenter/loginActivity"

    //用户服务暴露模块
    const val userService: String = "/userCenter/userService"
    //app模块暴露模块（TODO 待将app模块功能迁移成模块化）
    const val appService: String = "/app/appService"

    //收藏页面
    const val collectPage = "/userCenter/collectPage"


    //拦截器--登录拦截
    const val intercept_login = 1

    //降级界面
    const val lost_page = "/lost/lost_page"


    //测试总入口
    const val test_entry:String = "/test/test_entry"
}