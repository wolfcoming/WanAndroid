package com.czy.lib_log;

public class HiThreadFormatter implements HiLogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "\n Thread:" + data.getName();
    }
}