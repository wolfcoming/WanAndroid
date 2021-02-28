package com.czy.lib_log.format;

public class HiThreadFormatter implements HiLogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "线程信息：Thread:" + data.getName();
    }
}