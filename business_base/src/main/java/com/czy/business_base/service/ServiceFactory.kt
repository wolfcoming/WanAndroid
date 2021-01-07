package com.czy.business_base.service

import com.czy.business_base.service.emptyImpl.AppServiceEmptImpl
import com.czy.business_base.service.emptyImpl.UserServiceEmptImpl

/**
 * 服务工厂，供业务层调用
 */
class ServiceFactory {
    companion object {

        private var mUserService: UserService = UserServiceEmptImpl()
        private var mAppService: AppService = AppServiceEmptImpl()

        fun setUserService(userService: UserService) {
            this.mUserService = userService
        }

        fun getUserService(): UserService {
            return mUserService
        }

        fun setAppService(appService: AppService) {
            this.mAppService = appService
        }

        fun getAppService(): AppService {
            return this.mAppService
        }
    }
}