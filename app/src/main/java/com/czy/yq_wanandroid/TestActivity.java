package com.czy.yq_wanandroid;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.czy.yq_wanandroid.entity.WxArticle;
import com.czy.yq_wanandroid.net.BaseResult;
import com.czy.yq_wanandroid.net.WanApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class TestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WanApiService.Companion.getWanApi().getWxarticle().enqueue(new Callback<BaseResult<List<WxArticle>>>() {
            @Override
            public void onResponse(Call<BaseResult<List<WxArticle>>> call, Response<BaseResult<List<WxArticle>>> response) {

            }

            @Override
            public void onFailure(Call<BaseResult<List<WxArticle>>> call, Throwable t) {

            }
        });
    }
}
