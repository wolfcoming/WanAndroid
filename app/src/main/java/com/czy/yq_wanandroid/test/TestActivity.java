package com.czy.yq_wanandroid.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.czy.yq_wanandroid.R;
import com.example.lib_imageloader.image.ImageLoaderUtil;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import org.jetbrains.annotations.NotNull;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String url =
                "https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1819216937,2118754409&fm=26&gp=0.jpg";
        RecyclerView recyclerView = findViewById(R.id.recycyle);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TestRvAdapter(this,recyclerView));
        findViewById(R.id.btnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoViewer.INSTANCE.setClickSingleImg(url,null)
                        .setShowImageViewInterface(new PhotoViewer.ShowImageViewInterface() {
                            @Override
                            public void show(@NotNull ImageView iv, @NotNull String url) {
                                ImageLoaderUtil.getInstance().loadImage(url,iv);
                            }
                        })
                        .start(TestActivity.this);
            }
        });
    }


}
