package com.czy.lib_base.flowResult;

import android.content.Intent;

public interface Observer {
    void update(int resultCode, Intent data);
}
