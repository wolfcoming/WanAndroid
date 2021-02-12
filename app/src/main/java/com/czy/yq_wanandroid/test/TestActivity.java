package com.czy.yq_wanandroid.test;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.czy.yq_wanandroid.R;

import java.util.HashMap;
import java.util.concurrent.Executors;

public class TestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Executors.newFixedThreadPool(4);
        Executors.newSingleThreadExecutor();
        Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(10);
    }


    public void test(){

        Class<TestActivity> testActivityClass = TestActivity.class;
        HashMap<Class<?>,Object> classHashMap = new HashMap<>();
        classHashMap.put(testActivityClass,new TestActivity() );
    }

}
