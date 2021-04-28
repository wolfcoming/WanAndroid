package com.czy.business_base.flowResult;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author yangqing
 * @time 2021/4/16 15:46
 * @describe 请求简单封装（传递参数，抛出结果作用）
 */
class Request implements Parcelable {
    Intent intent;
    IResult observer;
    int requestCode;

    protected Request(Parcel in) {
        intent = in.readParcelable(Intent.class.getClassLoader());
        requestCode = in.readInt();
    }

    public static final Creator<Request> CREATOR = new Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(intent, flags);
        dest.writeInt(requestCode);
    }
}



