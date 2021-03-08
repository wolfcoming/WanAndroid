package com.czy.business_base.dataSave;

import android.content.Context;

import androidx.annotation.NonNull;

public class DataSaveProxy implements IDataSave{

    private static DataSaveProxy instance;

    private IDataSave dataSave;
    private DataSaveProxy(){
//        dataSave = new SpDataSave();//建议一个应用只使用一种存储方式，这里不暴露切换代理对象方法
        dataSave = new MmkvDataSave();//建议一个应用只使用一种存储方式，这里不暴露切换代理对象方法
    }

    public static DataSaveProxy getInstance(){
        if(instance == null){
            synchronized (DataSaveProxy.class){
                if(instance == null){
                    instance =new DataSaveProxy();
                }
            }
        }
        return instance;
    }


    @Override
    public void init(Context context, String storePath) {
        dataSave.init(context,storePath);
    }

    @Override
    public void put(@NonNull String key, String value) {
        dataSave.put(key,value);
    }

    @Override
    public void put(@NonNull String key, int value) {
        dataSave.put(key,value);
    }

    @Override
    public void put(@NonNull String key, float value) {
        dataSave.put(key,value);
    }

    @Override
    public void put(@NonNull String key, long value) {
        dataSave.put(key,value);
    }

    @Override
    public void put(@NonNull String key, boolean value) {
        dataSave.put(key,value);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return dataSave.getString(key,defaultValue);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return dataSave.getInt(key,defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return dataSave.getFloat(key,defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return dataSave.getLong(key,defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return dataSave.getBoolean(key,defaultValue);
    }

    @Override
    public void remove(String key) {
        dataSave.remove(key);
    }

    @Override
    public void clear() {
        dataSave.clear();
    }
}
