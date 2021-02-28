package com.czy.lib_log.format;

/**
 *
 * @author yangqing
 * @time   2/25/21 6:16 PM
 * @describe 日志格式化
 */
public interface HiLogFormatter<T> {
    String format(T data);
}