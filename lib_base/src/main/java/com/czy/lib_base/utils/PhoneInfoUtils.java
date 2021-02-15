package com.czy.lib_base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.File;


/**
 * 手机信息工具类，提供获取手机信息的方法
 *
 */
public final class PhoneInfoUtils {




    /**
     * 获取MAC地址
     */
    public static String getMAC(Context context) {
        return MacUtil.getMac(context);
    }


    /**
     * 获取AndroidId
     */
    @Deprecated
    public static String getAndoidId() {
        return Secure.ANDROID_ID;
    }

    public static String getAndoidId(Context context) {
        try {
            final String androidId;
            androidId = Secure.getString(
                    context.getContentResolver(),
                    Secure.ANDROID_ID);
            return androidId;
        } catch(Throwable t) {
            return "";
        }
    }

    /**
     * 获取设备的名称
     */
    public static String getModelName() {
        return Build.MODEL;
    }

    /**
     * 获取设备的开发名称或代号
     */
    public static String getProductName() {
        return Build.PRODUCT;
    }

    /**
     * 获取手机屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }



    /**
     * 判断是否存在SIM卡
     *
     */
    public static boolean hasSIM(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimState() != TelephonyManager.SIM_STATE_ABSENT;
    }



    /**
     * 查询指定目录空间信息
     */
    public static void getSizeInfo(File path, SizeInfo info) {
        try { // crash:64261643
            StatFs statfs = new StatFs(path.getPath());

            long blockSize = statfs.getBlockSize();// 获取block的SIZE
            info.availdSize = statfs.getAvailableBlocks() * blockSize;
            info.totalSize = statfs.getBlockCount() * blockSize;
        } catch (Exception e) {
        }
    }

    /**
     *
     * 大小信息
     */
    public static class SizeInfo {
        /**
         * 可用大小
         */
        public long availdSize;

        /**
         * 总共大小
         */
        public long totalSize;

        /**
         * 已用百分比
         *
         * @return 百分比值
         */
        public int percent() {
            int percent = 0;
            if (totalSize > 0) {
                float hadused = (float) (totalSize - availdSize) / (float) totalSize;
                percent = (int) (hadused * 100);
            }
            return percent;
        }
    }



    /**
     * 获取平台
     * @author josephguo
     */
    public static String getBoard() {
        return Build.BOARD;
    }




    public static boolean hasStorageCard() {
        try {
            String state= Environment.getExternalStorageState();
            return state.equals(Environment.MEDIA_MOUNTED);
        } catch (Throwable e) {
            return false;
        }
    }



    public static String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return code;
    }
}
