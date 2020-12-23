package com.czy.yq_wanandroid.aop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
class PerformanceAop {

    @Around("call(* com.czy.yq_wanandroid.business.home.home.HomeFragment.**(..))")
    public void getTime(ProceedingJoinPoint joinPoint) {
        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.e("TAG", "getTime: " + (System.currentTimeMillis() - time));
    }
}
