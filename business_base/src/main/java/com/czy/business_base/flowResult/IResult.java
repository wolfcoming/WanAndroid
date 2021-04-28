package com.czy.business_base.flowResult;

import android.content.Intent;

/**
 * @author yangqing
 * @time 2021/4/16 15:42
 * @describe 结果规范
 */
public interface IResult {
    void result(int requestCode, int resultCode, Intent data);
}
