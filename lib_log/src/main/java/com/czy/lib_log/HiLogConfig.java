package com.czy.lib_log;

import java.util.Objects;

public abstract class HiLogConfig {
    static int MAX_LEN =50;
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
        return false;
    }

    public int stackTraceDepth() {
        return 5;
    }

    public HiLogPrinter[] printers() {
        return null;
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
