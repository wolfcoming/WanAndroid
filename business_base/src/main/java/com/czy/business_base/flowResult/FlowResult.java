package com.czy.business_base.flowResult;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


/**
 * @author yangqing
 * @time 2021/4/16 14:58
 * @describe 链式调用startActivityForResult
 */
public class FlowResult {
    private FlowResult() {
    }

    public static class Builder {
        Context context;
        Intent intent;
        public IResult mIResult;//结果监听
        public INoResultBack mINoResultBack;//目标页面没有给我们抛回来结果的时候触发
        int mQuestCode = 0x1001;//请求码
//        int mResultCode = 0x10002; //响应码

        public Builder(Context t) {
            context = t;
        }

        /**
         * 设置intent
         *
         * @param i
         * @return
         */
        public Builder setIntent(Intent i) {
            this.intent = i;
            return this;
        }


        /**
         * 设置结果回调监听
         *
         * @param result
         * @return
         */
        public Builder addResultListener(IResult result) {
            this.mIResult = result;
            return this;
        }

        /**
         * 设置无结果返回回调
         *
         * @param noResultBack
         * @return
         */
        public Builder addNoResultBackListener(INoResultBack noResultBack) {
            this.mINoResultBack = noResultBack;
            return this;
        }


        public void call() {
            call(mQuestCode);
        }

        /**
         * 吊起目标界面
         *
         * @param requestCode 请求码
         */
        public void call(int requestCode) {

            if (!(context instanceof FragmentActivity)) {
                throw new IllegalArgumentException("context 必须是FragmentActivity类型");
            }
            if (intent == null) {
                throw new IllegalArgumentException("intent必须传递");
            }
            FragmentActivity activity = (FragmentActivity) this.context;
            int requestedOrientation = activity.getRequestedOrientation();
            if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT &&
                    requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                //咱未处理屏幕旋转逻辑，因绝大部分应用均会屏幕方向固定，暂不处理
                throw new IllegalArgumentException("暂不支持屏幕旋转的activity");
            }

            Request request = new Request(intent, requestCode);
            request.subscribe(new IResult() {
                @Override
                public void result(int requestCode, int resultCode, Intent data) {
                    if (resultCode == 0 && data == null) {
                        if (mINoResultBack != null) {
                            mINoResultBack.noResultCallBack();
                        }
                    } else {
                        if (mIResult != null) {
                            mIResult.result(requestCode, resultCode, data);
                        }
                    }
                }
            });
            final VirtualFragment appFragment = new VirtualFragment();
            appFragment.setRequest(request);
            FragmentManager supportFragmentManager = ((FragmentActivity) this.context).getSupportFragmentManager();
            supportFragmentManager.beginTransaction().replace(android.R.id.content, appFragment).commitAllowingStateLoss();
            supportFragmentManager.executePendingTransactions();
        }


    }
}
