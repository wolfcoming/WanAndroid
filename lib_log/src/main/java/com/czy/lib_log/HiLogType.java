package com.czy.lib_log;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author yangqing
 * @time   2/25/21 5:41 PM
 * @describe 日志类型
 */
public class HiLogType {

    @IntDef({V,D,I,W,E,A})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {}

    public static final int V = Log.VERBOSE;
    public static final int D = Log.DEBUG;
    public static final int I = Log.INFO;
    public static final int W = Log.WARN;
    public static final int E = Log.ERROR;
    public static final int A = Log.ASSERT;
}
