package com.czy.business_base.tasks;

import com.czy.business_base.dataSave.DataSaveProxy;
import com.czy.business_base.launchstarter.task.Task;

public class InitDataSave extends Task {
    @Override
    public void run() {
        DataSaveProxy.getInstance().init(mContext, "common_data_save");
    }

    @Override
    public boolean needWait() {
        return true;
    }
}
