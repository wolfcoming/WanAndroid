package com.czy.test_model;

import android.widget.Toast;

import com.czy.business_base.BaseActivity;
import com.czy.business_base.net.Transformer;
import com.czy.business_base.api.WanApi;
import com.czy.business_base.net.ApiSubscriberHelper;
import com.czy.business_base.net.entity.BaseResult;
import com.czy.lib_net.ApiException;
import com.czy.lib_net.CommonApiService;

public class TestNetRequestActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn).setOnClickListener(v -> {
            CommonApiService.Companion.getRequest(WanApi.class)
                    .collectArticle(12554)
                    .compose(Transformer.threadSwitch())
                    .subscribe(new ApiSubscriberHelper<BaseResult<String>>() {
                        @Override
                        protected void onResult(BaseResult<String> stringBaseResult) {
                            Toast.makeText(TestNetRequestActivity.this, stringBaseResult.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        protected void onFailed(ApiException msg) {
                            Toast.makeText(TestNetRequestActivity.this, msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }


    @Override
    public void initData() {

    }
}
