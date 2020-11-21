package com.czy.yq_wanandroid.flowResult;

import android.content.Intent;

interface Observer {
    void update(int resultCode, Intent data);
}
