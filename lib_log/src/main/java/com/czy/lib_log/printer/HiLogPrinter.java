package com.czy.lib_log.printer;

import androidx.annotation.NonNull;

import com.czy.lib_log.HiLogConfig;

public interface HiLogPrinter {
    void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString);
}
