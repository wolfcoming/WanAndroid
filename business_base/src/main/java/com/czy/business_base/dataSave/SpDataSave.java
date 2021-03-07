package com.czy.business_base.dataSave;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import static android.content.Context.MODE_PRIVATE;


class SpDataSave implements IDataSave {
    private SharedPreferences sp;

    @Override
    public void init(Context context, String storePath) {
        sp = context.getSharedPreferences(storePath,MODE_PRIVATE);
    }

    @Override
    public void put(@NonNull String key, String value) {
        sp.edit().putString(key,value).apply();
    }

    @Override
    public void put(@NonNull String key, int value) {
        sp.edit().putInt(key,value).apply();
    }

    @Override
    public void put(@NonNull String key, float value) {
        sp.edit().putFloat(key,value).apply();
    }

    @Override
    public void put(@NonNull String key, long value) {
        sp.edit().putLong(key,value).apply();
    }

    @Override
    public void put(@NonNull String key, boolean value) {
        sp.edit().putBoolean(key,value).apply();
    }

    @Override
    public String getString(String key, String defaultValue) {
        return sp.getString(key,defaultValue);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return sp.getInt(key,defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key,defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return sp.getLong(key,defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key,defaultValue);
    }

    @Override
    public void remove(String key) {
        sp.edit().remove(key).apply();
    }

    @Override
    public void clear() {
        sp.edit().clear().apply();
    }
}
