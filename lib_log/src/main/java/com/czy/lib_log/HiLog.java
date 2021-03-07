package com.czy.lib_log;


import androidx.annotation.NonNull;

import com.czy.lib_log.printer.HiLogPrinter;
import com.czy.lib_log.utils.HiStackTraceUtil;

import java.util.ArrayList;
import java.util.List;

import static com.czy.lib_log.HiLogConfig.CACHE_MAX;

/**
 * @author yangqing
 * @time 2/25/21 5:37 PM
 * @describe 打印堆信息，文件输出，模拟控制台
 */
public class HiLog {

    private static final String HI_LOG_PACKAGE;

    static {
        String className = HiLog.class.getName();
        HI_LOG_PACKAGE = className.substring(0, className.lastIndexOf(".") + 1);
    }

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


    public static void w(Object... objects) {
        log(HiLogType.W, objects);
    }

    public static void wt(String tag, Object... objects) {
        log(HiLogType.W, tag, objects);
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
        if(HiLogManager.getInstance()==null) return;//日志模块还未加载
        log(type, HiLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(HiLogManager.getInstance().getConfig(), type, tag, contents);
    }
    //记录需要在可视化控制台打印的历史信息
    public static ArrayList<HiLogMo> historyLogs = new ArrayList<>();
    public static void log(HiLogConfig hiLogConfig, @HiLogType.TYPE int type, String tag, Object... objects) {
        if (!hiLogConfig.enable()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (hiLogConfig.includeThread()) {
            String threadInfo = hiLogConfig.HI_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }

        if (hiLogConfig.stackTraceDepth() > 0) {
            //打印指定深度的堆栈信息（ 过滤掉log日志相关的方法栈信息）
            StackTraceElement[] croppedRealStackTrack = HiStackTraceUtil.getCroppedRealStackTrack(new Throwable().getStackTrace(), HI_LOG_PACKAGE, hiLogConfig.stackTraceDepth());
            String stackTrace = hiLogConfig.HI_STACK_TRACE_FORMATTER.format(croppedRealStackTrack);
            sb.append(stackTrace).append("\n");
        }

        String body = parseBody(objects, hiLogConfig);
        sb.append(body);
        List<HiLogPrinter> printers =HiLogManager.getInstance().getPrinters();
        if (printers == null) {
            return;
        }
        String msg = sb.toString();

        HiLogMo hiLogMo = new HiLogMo(System.currentTimeMillis(), type, tag, msg);
        historyLogs.add(hiLogMo);
        if(historyLogs.size()>CACHE_MAX){
            //只保留CACHE_MAX 条（TODO 是否有效率更高的方式?)
            historyLogs.remove(0);
        }

        for (HiLogPrinter printer : printers) {
            printer.print(hiLogConfig, type, tag, msg);
        }
    }

    private static String parseBody(@NonNull Object[] contents, @NonNull HiLogConfig config) {
        StringBuilder sb = new StringBuilder();
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
