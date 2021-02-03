package com.czy.business_base.flowResult;

import android.content.Intent;

public interface Observer {
    void update(int resultCode, Intent data);
}
