package com.czy.yq_wanandroid.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.czy.lib_ui.refresh.HiRefreshLayout;
import com.czy.lib_ui.refresh.HiTextOverView;
import com.czy.yq_wanandroid.R;
import com.example.lib_imageloader.image.ImageLoaderUtil;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import org.jetbrains.annotations.NotNull;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        HiRefreshLayout hiRefreshLayout = findViewById(R.id.refresh);
        hiRefreshLayout.setRefreshOverView(new HiTextOverView(this));
    }


}
