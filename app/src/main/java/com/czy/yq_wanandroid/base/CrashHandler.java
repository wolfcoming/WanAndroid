package com.czy.yq_wanandroid.base;

import android.content.Context;

import com.czy.business_base.MyActivityManager;
import com.czy.lib_base.utils.file.FileIOUtils;

import java.io.File;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static volatile CrashHandler crashHandler;

    private Context context;

    private CrashHandler() {
    }

    public void init(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CrashHandler getCrashHander() {
        if (crashHandler == null) {
            synchronized (CrashHandler.class) {
                if (crashHandler == null) {
                    crashHandler = new CrashHandler();
                }
            }
        }
        return crashHandler;
    }

    @Override
    public void uncaughtException(Thread t, final Throwable e) {
        try{
            StackTraceElement[] stackTrace = e.getStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("崩溃时间:\t" + System.currentTimeMillis() + "\n崩溃线程:\t" + t.getName() + "\n");
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(stackTraceElement.toString() + "\n");
            }
            sb.append("=====================================================================\n\n");
            String result = sb.toString();
            //保存错误栈至文件
            FileIOUtils.writeFileFromString(context.getFilesDir() + File.separator + "log" + File.separator + "crash.txt", result, true);

        }finally {
            MyActivityManager.getActivityManager().popAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

    }
}