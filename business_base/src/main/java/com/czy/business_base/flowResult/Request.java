package com.czy.business_base.flowResult;

import android.content.Intent;

/**
 * @author yangqing
 * @time 2021/4/16 15:46
 * @describe 请求简单封装（传递参数，抛出结果作用）
 */
class Request {
    Intent intent;
    IResult observer;
    int requestCode;

    protected void post(int request, int resultCode, Intent data) {
        observer.result(request, resultCode, data);
    }

    protected void subscribe(IResult observer) {
        this.observer = observer;
    }

    public Request(Intent intent, int request) {
        this.intent = intent;
        this.requestCode = request;
    }
}



