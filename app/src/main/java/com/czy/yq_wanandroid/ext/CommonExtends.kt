package com.yangqing.record.ext

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.czy.yq_wanandroid.net.ApiErrorHandlerUtil
import com.czy.yq_wanandroid.net.ApiException
import com.infoholdcity.basearchitecture.self_extends.log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

//rxjava 线程切换扩展
fun <T> Observable<T>.threadSwitch(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
// rxjava 统一错误处理扩展
fun <T> Observable<T>.subscribeDeal(
    onNext: (reult: T) -> Unit,
    onError: (error: ApiException) -> Unit,
    onComplete: () -> Unit
): Disposable {
    return this.subscribe(
        {
            "onNext".log()
            //此处也可以做 统一错误码异常处理（缺点是 无法解决java代码调用问题）
            onNext(it)
        },
        {
            "throwable".log()
            val wanApiException = ApiErrorHandlerUtil.getWanApiException(it)
            onError(wanApiException)
        },
        {
            "onComplete".log()
            onComplete
        }
    )
}


fun <T> Observable<T>.subscribeDeal(
    onNext: (result: T) -> Unit
): Disposable {
    return this.subscribeDeal(onNext, {}, {})
}

fun <T> Observable<T>.subscribeDeal(
    onNext: (result: T) -> Unit,
    onError: (error: ApiException) -> Unit
): Disposable {
    return this.subscribeDeal(onNext, onError, {})
}

fun <T> Observable<T>.subscribeDeal(
    onStart: () -> Unit,
    onNext: (result: T) -> Unit,
    onError: (error: ApiException) -> Unit,
    onComplete: () -> Unit
): Disposable {
    onStart
    return this.subscribeDeal(onNext, onError, onComplete)
}







