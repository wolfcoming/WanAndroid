package com.czy.lib_log;


import com.czy.lib_log.format.HiStackTraceFormatter;
import com.czy.lib_log.format.HiThreadFormatter;

public abstract class HiLogConfig {
    public static int MAX_LEN =500;
    public static int CACHE_MAX = 200;//缓存的日志数量
    static HiThreadFormatter HI_THREAD_FORMATTER = new HiThreadFormatter();
    static HiStackTraceFormatter HI_STACK_TRACE_FORMATTER = new HiStackTraceFormatter();

    public JsonParser injectJsonParser() {
        return null;
    }

    public String getGlobalTag(){
        return "HiLog";
    }

    /**
     *
     * @author yangqing
     * @time   2/25/21 6:23 PM
     * @describe 是否包含线程信息
     */
    public boolean includeThread() {
        return true;
    }

    public int stackTraceDepth() {
        return 5;
    }

    /**
     * 是否启用log
     * @return
     */
    public boolean enable(){
        return true;
    }

    public interface JsonParser {
        String toJson(Object src);
    }
}
