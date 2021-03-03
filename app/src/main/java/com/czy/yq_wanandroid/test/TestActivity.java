package com.czy.yq_wanandroid.test;

import android.view.View;

import com.czy.business_base.BaseActivity;
import com.czy.lib_ui.RotatingRing;
import com.czy.yq_wanandroid.R;

public class TestActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {
        findViewById(R.id.btn).setOnClickListener(v -> {
            RotatingRing rotatingRing = findViewById(R.id.ring);
            RotatingRing rotatingRing2 = findViewById(R.id.ring);
            rotatingRing2.startAnimal();
        });
    }

    @Override
    public void initData() {

    }
}
