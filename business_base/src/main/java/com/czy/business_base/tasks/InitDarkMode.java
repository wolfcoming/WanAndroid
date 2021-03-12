package com.czy.business_base.tasks;

import androidx.appcompat.app.AppCompatDelegate;

import com.czy.business_base.dataSave.DataSaveProxy;
import com.czy.business_base.launchstarter.task.Task;

import java.util.ArrayList;
import java.util.List;

public class InitDarkMode extends Task {
    @Override
    public void run() {
        boolean darkMode = DataSaveProxy.getInstance().getBoolean("darkMode", false);
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public boolean needWait() {
        return true;
    }
    @Override
    public List<Class<? extends Task>> dependsOn() {
        List<Class<? extends Task>> list = new ArrayList<>();
        list.add(InitDataSave.class);
        return list;
    }

}
