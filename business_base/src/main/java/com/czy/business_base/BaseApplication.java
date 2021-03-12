package com.czy.business_base;

import android.app.Application;
import android.content.Context;

import com.czy.business_base.launchstarter.TaskDispatcher;
import com.czy.business_base.tasks.InitArouter;
import com.czy.business_base.tasks.InitBuglyTask;
import com.czy.business_base.tasks.InitCompontService;
import com.czy.business_base.tasks.InitDarkMode;
import com.czy.business_base.tasks.InitDataSave;
import com.czy.business_base.tasks.InitLogTask;
import com.czy.business_base.tasks.InitSmartRefresh;
import com.czy.lib_base.utils.ContentWrapperUtils;

public abstract class BaseApplication extends Application {

    public Context mContext;
    public TaskDispatcher taskDispatcher;

    public abstract void initMouduleApplication();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ContentWrapperUtils.INSTANCE.init(mContext);
        TaskDispatcher.init(this);
        taskDispatcher = TaskDispatcher.createInstance();
        TaskDispatcher taskDispatcher = this.taskDispatcher
                .addTask(new InitBuglyTask())
                .addTask(new InitArouter())
                .addTask(new InitSmartRefresh())
                .addTask(new InitCompontService())
                .addTask(new InitDataSave())
                .addTask(new InitDarkMode())
                .addTask(new InitLogTask());
        initMouduleApplication();
        taskDispatcher.start();

        this.taskDispatcher.await();
    }
}
