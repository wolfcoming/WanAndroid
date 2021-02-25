package com.czy.lib_log;


import android.util.Log;

import androidx.annotation.NonNull;

/**
 * @author yangqing
 * @time 2/25/21 5:37 PM
 * @describe 打印堆信息，文件输出，模拟控制台
 */
public class HiLog {
    public static void v(Object... objects) {
        log(HiLogType.V, objects);
    }

    public static void vt(String tag, Object... objects) {
        log(HiLogType.V, tag, objects);
    }

    public static void i(Object... objects) {
        log(HiLogType.I, objects);
    }

    public static void it(String tag, Object... objects) {
        log(HiLogType.I, tag, objects);
    }

    public static void d(Object... objects) {
        log(HiLogType.D, objects);
    }

    public static void dt(String tag, Object... objects) {
        log(HiLogType.D, tag, objects);
    }

    public static void e(Object... objects) {
        log(HiLogType.E, objects);
    }

    public static void et(String tag, Object... objects) {
        log(HiLogType.E, tag, objects);
    }

    public static void a(Object... objects) {
        log(HiLogType.A, objects);
    }

    public static void at(String tag, Object... objects) {
        log(HiLogType.A, tag, objects);
    }

    public static void log(@HiLogType.TYPE int type, Object... contents) {
        log(type, HiLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(HiLogManager.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(HiLogConfig hiLogConfig, @HiLogType.TYPE int type, String tag, Object... objects) {
        if(!hiLogConfig.enable()){
            return;
        }
        StringBuilder sb = new StringBuilder();
        String body = parseBody(objects,hiLogConfig);
        sb.append(body);
        Log.println(type, tag, sb.toString());
    }

    private static String parseBody(@NonNull Object[] contents, @NonNull HiLogConfig config) {
        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
