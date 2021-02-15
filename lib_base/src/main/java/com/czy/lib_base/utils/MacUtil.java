package com.czy.lib_base.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jasonzhan on 2017/11/10.
 * Android 6.x开始传传统方法默认获取不到mac，这里尝试多种方法去获取
 *
 旧方法：调用WifiManager的api，6.0以下需要声明android.permission.ACCESS_WIFI_STATE权限。源码注释在6.x以上要声明android.permission.LOCAL_MAC_ADDRESS权限，否则返回02:00:00:00:00:00，但实测即使声明该权限也会返回这个默认值，而且不会提示用户授权。
 新方法A：通过反射获取wifi的interface name,再调用NetworkInterface.getByName(name)得到mac，需要声明android.permission.INTERNET权限。
 新方法B：通过反射获取wifi的interface name,再读/sys/class/net/[name]/address文件,无需声明权限。

 各方法表现：
 1.旧方法:在6.x以上都失败，返回02:00:00:00:00:00
 2.新方法A：各机型都成功
 3.新方法B: 不同品牌表现不同
 Android6.x：都成功
 Android7.x：和厂商策略有关，不同品牌表现不同，vivo、一加、三星成功，OPPO、小米、华为失败
 Android8.x：只找到Pixel一款,无法获取mac。猜测和7.x一样,不同品牌表现不同
 失败原因是没有权限读文件（java.io.FileNotFoundException: /sys/class/net/wlan0/address (Permission denied)）
 以上结果基于无root情况，由于7.0以上目前基本无法root，所以想通过root解决7.0以上版本的问题也不太可行

 综合：以上方法都无需root。在6.x以上，方法A适配最好但需要声明权限，方法B无需声明权限但适配一般。因此，可以各种方法都试一遍（传统方法->方法A->方法B）

 ----------------------------------------------------------
 系统版本	机型              旧方法	  新方法A	  新方法B
 ------
 8.0.0	    Google Pixel XL	    N	    Y	        N

 7.1.1	    vivo X9i	        N	    Y  	        Y
 7.1.1	    ONEPLUS A3010	    N	    Y	        Y
 7.1.1	    MI 6	            N	    Y	        N
 7.1.1	    OPPO R9sk	        N	    Y	        N

 7.0.0	    ONEPLUS A3000	    N	    Y	        Y
 7.0.0	    三星 GALAXY S6	    N	    Y	        Y
 7.0.0	    三星 SM-G9550	    N	    Y	        Y
 7.0.0	    小米 MI 5	        N	    Y	        N
 7.0.0	    华为 Mate 9	        N	    Y	        N
 7.0.0	    华为 P10	        N	    Y	        N

 6.0.1	    小米 MI MAX	        N	    Y	        Y
 6.0.1	    vivo Xplay6	        N	    Y	        Y
 6.0.1	    Nexus 6P	        N	    Y	        Y
 6.0.1	    SM-C9000	        N	    Y	        Y
 ----------------------------------------------------------
 */
public class MacUtil {
    private static final String TAG = "MacUtil";

    /**
     * 增强型获取mac的方式
     *
     * http://yrom.net/blog/2015/10/28/android-m-getting-wifi-macaddress-1/
     */
    public static String getMac(Context context) {

        String mac = "";

        try {
            // 1.先用官方接口
            mac = getMacOld(context);

            if (TextUtils.isEmpty(mac) || "02:00:00:00:00:00".equals(mac)) {

                // 通过反射获得wifi的interface name
                String interfaceName = getSysPropByReflect("wifi.interface");
                if (TextUtils.isEmpty(interfaceName)) {
                    interfaceName = "wlan0";
                }

                // 2.用API适配性比读文件好，优先使用
                mac = getMacByAPI(interfaceName);

                // 3.如果API不行（不知道什么时候这个API也给封了），直接读文件
                if (TextUtils.isEmpty(mac) || "02:00:00:00:00:00".equals(mac)) {
                    mac = getMacFromFile(interfaceName);
                }
            }
        } catch (Throwable e) {
        }

        return mac;
    }

    /**
     * 获取MAC地址（传统方法，在Android6.x开始失效）
     *
     * 需权限android.permission.ACCESS_WIFI_STATE
     * 源码注释在6.x以上要声明android.permission.LOCAL_MAC_ADDRESS权限，否则返回02:00:00:00:00:00，
     * 但实测即使声明该权限也会返回这个默认值，而且不会提示用户授权
     *
     */
    private static String getMacOld(Context context) {
        String mac = "";

        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                mac = info.getMacAddress();
            }
        } catch (Throwable e) {
        }

        return mac != null ? mac : "";
    }

    /**
     * 通过NetworkInterface.getByName获取mac
     *
     * 需要权限android.permission.INTERNET
     */
    private static String getMacByAPI(String interfaceName) {
        String mac = "";

        try {
            NetworkInterface nif = NetworkInterface.getByName(interfaceName);
            byte[] macBytes = nif.getHardwareAddress();
            if (macBytes == null) {
                return mac;
            }

            StringBuilder sb = new StringBuilder();
            for (byte b : macBytes) {
                sb.append(String.format("%02x:", b));
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            mac = sb.toString();
        } catch (Throwable e) {
            // libcore.io.ErrnoException: socket failed: EACCES (Permission denied)
        }

        return mac;
    }

    /**
     * 直接读文件
     *
     * 不用声明权限，但访问文件有可能抛出异常
     * java.io.FileNotFoundException: /sys/class/net/wlan0/address (Permission denied)
     *
     * @param interfaceName
     * @return
     */
    private static String getMacFromFile(String interfaceName) {
        String mac = "";

        try {
            File file = new File(String.format("/sys/class/net/%s/address", interfaceName));
            List<String> lines = readLines(file);
            if (lines != null && lines.size() == 1) {
                mac = lines.get(0);
                if (!TextUtils.isEmpty(mac)) {
                    mac = mac.trim();
                }
            }
        } catch(Throwable e) {
        }

        return mac;
    }

    /**
     * 获取设备的某个属性值
     * rr.build_product = getSysPropByReflect("ro.build.product");
     * interfaceName = getSysPropByReflect("wifi.interface");
     *
     * @param name 属性名
     * @return 对应的属性值
     */
    public static String getSysPropByReflect(String name) {
        String ret = "";

        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", String.class);
            method.setAccessible(true);
            ret = (String) method.invoke(null, name);
            if (ret == null) {
                ret = "";
            }
        } catch (Throwable e) {
        }

        return ret;
    }

    public static List<String> readLines(File file) {
        List<String> lines = new ArrayList<String>();

        FileInputStream fis = null; // 读byte数组
        InputStreamReader isr = null; // 读char数组, 可指定按什么编码方式读
        BufferedReader br = null; // 读char数组，可按行读

        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Throwable e) {
        } finally {
            try {
                if (null != fis)
                    fis.close();
                if (null != isr)
                    isr.close();
                if (null != br)
                    br.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return lines;
    }
}

