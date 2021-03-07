package com.czy.business_base.dataSave;

import android.content.Context;

import androidx.annotation.NonNull;

import com.czy.lib_log.HiLog;
import com.tencent.mmkv.MMKV;

class MmkvDataSave implements IDataSave{

    private MMKV mmkv;

    @Override
    public void init(Context context, String storePath) {
        String rootDir = MMKV.initialize(storePath);
        HiLog.e(rootDir);
        mmkv = MMKV.defaultMMKV();
    }

    @Override
    public void put(@NonNull String key, String value) {
        mmkv.encode(key,value);
    }

    @Override
    public void put(@NonNull String key, int value) {
        mmkv.encode(key,value);
    }

    @Override
    public void put(@NonNull String key, float value) {
        mmkv.encode(key,value);
    }

    @Override
    public void put(@NonNull String key, long value) {
        mmkv.encode(key,value);
    }

    @Override
    public void put(@NonNull String key, boolean value) {
        mmkv.encode(key,value);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return mmkv.decodeString(key,defaultValue);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return mmkv.decodeInt(key,defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return mmkv.decodeFloat(key,defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return mmkv.decodeLong(key,defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return mmkv.decodeBool(key,defaultValue);
    }

    @Override
    public void remove(String key) {
        mmkv.remove(key);
    }

    @Override
    public void clear() {
        mmkv.clear();
    }
}
