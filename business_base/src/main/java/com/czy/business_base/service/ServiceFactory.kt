package com.czy.business_base.service

import com.czy.business_base.service.emptyImpl.UserServiceEmptImpl

/**
 * 服务工厂，供业务层调用
 */
class ServiceFactory {
    companion object {

        private var mUserService: UserService = UserServiceEmptImpl()

        fun setUserService(userService: UserService) {
            this.mUserService = userService
        }

        fun getUserService(): UserService {
            return mUserService
        }
    }
}