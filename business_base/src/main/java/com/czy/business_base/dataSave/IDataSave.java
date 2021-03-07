package com.czy.business_base.dataSave;

import android.content.Context;

import androidx.annotation.NonNull;

interface IDataSave {

    void init(Context context,String storePath);

    void put(@NonNull String key, String value);

    void put(@NonNull String key, int value);

    void put(@NonNull String key, float value);

    void put(@NonNull String key, long value);

    void put(@NonNull String key, boolean value);

    String getString(String key, String defaultValue);

    int getInt(String key, int defaultValue);

    float getFloat(String key, float defaultValue);

    long getLong(String key, long defaultValue);

    boolean getBoolean(String key, boolean defaultValue);

    void remove(String key);

    void clear();

}
