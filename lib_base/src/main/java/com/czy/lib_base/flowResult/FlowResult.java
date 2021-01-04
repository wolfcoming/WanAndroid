package com.czy.lib_base.flowResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


/**
 * @author yangqing
 * @time 2020/8/4 4:58 PM
 * @describe 链式得到ActivityForResult 的结果
 */
public class FlowResult {
    private FlowResult() {
    }


    public static class Builder {
        Context context;
        Intent intent;
        public IResult mIResult;//结果监听
        public ICancel mICancel;//取消监听
        int mResultCode = -10086;

        Bundle data = new Bundle();

        public Builder(Context t) {
            context = t;
        }

        /**
         * 设置intent
         * @param i
         * @return
         */
        public Builder setIntent(Intent i) {
            this.intent = i;
            return this;
        }

        /**
         * 过滤返回结果码
         *
         * @param resultCode
         * @return
         */
        public Builder filterResultCode(int resultCode) {
            this.mResultCode = resultCode;
            return this;
        }

        /**
         * 设置结果回调监听
         * @param result
         * @return
         */
        public Builder addResultListener(IResult result){
            this.mIResult = result;
            return this;
        }

        /**
         * 设置取消监听
         * @param cancel
         * @return
         */
        public Builder addCancelListener(ICancel cancel){
            this.mICancel = cancel;
            return this;
        }


        public void call() {
            if(mResultCode==-10086){
                mResultCode = Activity.RESULT_OK;
            }
            call(mResultCode);
        }

        private void call(final int mResultCode) {
            if (!(context instanceof Activity)){
                throw new IllegalArgumentException("context 必须是Activity类型");
            }
            if(intent==null){
                throw new IllegalArgumentException("intent必须传递");
            }
            if (!data.isEmpty()) {
                intent.putExtras(data);
            }

            Request request = new Request(intent);
            request.subscribe(new Observer() {
                @Override
                public void update(int resultCode, Intent data) {
                    if (resultCode == mResultCode) {
                        if(mIResult!=null){
                            mIResult.result(data);
                        }
                    } else {
                        if(mICancel!=null){
                            mICancel.cancel();
                        }
                    }
                }
            });

            final VirtualFragment appFragment = new VirtualFragment();
            appFragment.setRequest(request);

            ((Activity) context).getFragmentManager()
                    .beginTransaction().replace(android.R.id.content, appFragment)
                    .commitAllowingStateLoss();
        }


    }
}
