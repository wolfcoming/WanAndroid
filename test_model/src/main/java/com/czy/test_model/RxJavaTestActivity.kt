package com.czy.test_model

import com.czy.business_base.BaseActivity
import com.infoholdcity.basearchitecture.self_extends.log
import io.reactivex.rxjava3.core.*
import kotlinx.android.synthetic.main.activity_rxjava.*
import org.reactivestreams.Subscription

class RxJavaTestActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_rxjava
    }

    override fun initView() {
      btnTest.setOnClickListener {
          Flowable.create(object : FlowableOnSubscribe<String> {
              override fun subscribe(emitter: FlowableEmitter<String>?) {
                  emitter?.onNext("test")
                  emitter?.onComplete()
              }
          }, BackpressureStrategy.DROP)
              .subscribe(object :FlowableSubscriber<String>{
                  override fun onSubscribe(s: Subscription?) {
                      s?.request(Long.MAX_VALUE)
                      "onSubscribe".log()
                  }

                  override fun onNext(t: String?) {
                      "onNext: $t".log()
                  }

                  override fun onError(t: Throwable?) {
                      "onError: $t".log()
                  }

                  override fun onComplete() {
                      "onComplete".log()
                  }

              })


          Observable.create<String> { it->
              it.onNext("hahha")
          }.subscribe({
              "onNext$it".log()
          },{
              "onError".log()
          },{
              "onComplete".log()
          })
      }
    }

    override fun initData() {
    }
}