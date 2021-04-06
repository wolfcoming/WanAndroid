package com.czy.business_base.service

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.czy.business_base.ArouterConfig
import com.czy.business_base.service.emptyImpl.AppServiceEmptImpl
import com.czy.business_base.service.emptyImpl.UserServiceEmptImpl
import com.czy.lib_base.utils.ContentWrapperUtils
import com.infoholdcity.basearchitecture.self_extends.log

/**
 * 服务工厂，供业务层调用
 */
class ServiceFactory {
    companion object {

        private var mUserService: UserService? = null
        private var mAppService: AppService? = null

        fun getUserService(): UserService {
            if (mUserService != null) {
                return mUserService!!
            }
            val provider = getProvider(ArouterConfig.userService)
            mUserService = if (provider == null) {
                UserServiceEmptImpl()
            } else {
                provider as UserService
            }
            return mUserService!!
        }

        fun getAppService(): AppService {
            if (mAppService != null) return mAppService!!
            val provider = getProvider(ArouterConfig.appService)
            mAppService = if (provider == null) {
                AppServiceEmptImpl()
            } else {
                provider as AppService
            }
            return mAppService!!
        }

        fun getProvider(url: String): Any? {
            val navigation = ARouter.getInstance().build(url)
                .navigation(ContentWrapperUtils.mContext, MyNavigationCallback())
            return navigation
        }

        class MyNavigationCallback :
            NavigationCallback {
            override fun onFound(postcard: Postcard?) {
            }

            override fun onLost(postcard: Postcard?) {
                "找不到：$postcard?.uri.toString().log()".log()
            }

            override fun onArrival(postcard: Postcard?) {
            }

            override fun onInterrupt(postcard: Postcard?) {
            }

        }
    }

}