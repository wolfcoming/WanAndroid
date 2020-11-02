package com.czy.yq_wanandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.czy.yq_wanandroid.entity.WxArticle;
import com.czy.yq_wanandroid.net.BaseResult;
import com.czy.yq_wanandroid.net.WanApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.functions.Consumer;

public class TestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<? extends TextView> textViews = new ArrayList<Button>();

    }


    public void test(){
        WanApiService.Companion.getWanApi().getWxarticle4()
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Consumer<BaseResult<List<WxArticle>>>() {
                    @Override
                    public void accept(BaseResult<List<WxArticle>> listBaseResult) throws Throwable {

                    }
                });
    }
}
