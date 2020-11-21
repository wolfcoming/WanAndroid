package com.czy.yq_wanandroid.flowResult;

import android.content.Intent;

class Request {
    Intent intent;
    Observer observer;

    protected void post(int resultCode, Intent data) {
        observer.update(resultCode, data);
    }

    protected void subscribe(Observer observer) {
        this.observer = observer;
    }

    public Request(Intent intent) {
        this.intent = intent;
    }
}



