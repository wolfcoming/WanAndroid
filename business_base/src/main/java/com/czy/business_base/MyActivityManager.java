/*
 * *********************************************************************************************************
 *  * Copyright (C) 2017 SINOSUN Co.Ltd. All rights reserved.
 *  * 版权所有 (C) 2017 兆日科技有限公司. 保留所有权利.
 *  *
 *  * 本软件/源代码受中华人民共和国著作权法保护，除非法律允许或兆日科技书面许可，不得从事下列行为：
 *  * 1、删除本软件/源代码及其副本上关于著作权的信息
 *  * 2、对本软件进行反向工程、反向汇编、反向编译，或者以其他方式尝试发现本软件的源代码
 *  * 3、对本软件/源代码进行：使用、出租、出借、复制、修改、链接、转载、汇编、发表、出版、传播、建立镜像站点等
 *  * 4、其他未经兆日科技明示授权的行为
 *  **********************************************************************************************************
 */

package com.czy.business_base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import java.util.Stack;

/**
 * 类名        ：MyActivityManager
 * 描述        ：类功能描述
 *
 * @author 作者
 * 时间        ：2018/4/9 15:18
 */

public class MyActivityManager {
    private Stack<Activity> activityStack;
    private static MyActivityManager instance;

    private MyActivityManager() {
    }

    public Stack<Activity> getActivityStack() {
        return activityStack;
    }

    /**
     * 弹出activity, 不调用finish， 一般在onDestroy()中调用
     *
     * @param activity activity
     * @method: popActivity
     */
    public void popActivity(Activity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param activity activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 最后压入的activity
     *
     * @return activity
     */
    public Activity lastBeforeActivity() {
        int int2 = 2;
        if (activityStack != null && activityStack.size() >= int2) {
            Activity activity = activityStack.get(activityStack.size() - 2);
            return activity;
        } else {
            return null;
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls cls
     */
    public void finishActivity(Class cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }


    /**
     * 获取指定的Activity
     *
     * @param cls cls
     * @return activity
     */
    public Activity getActivity(Class cls) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
    }

    public Activity getActivityByName(String className) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().getSimpleName().equals(className)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * 退出到最后一层界面
     */
    public void popAllActivityExceptMain(String exceptActivityName) {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (activity != null
                    && !(activity.getClass().getSimpleName().equals(exceptActivityName))) {
                activityStack.get(i).finish();
                popActivity(activityStack.get(i));
            }
        }
    }

    /**
     * 关闭Activity 到Main
     */
    public void finishActivityToMain(String exceptActivityName) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (activity != null
                    && !(activity.getClass().getSimpleName().equals(exceptActivityName))) {
                activityStack.remove(activity);
                activity.finish();
            }
        }
    }

    public static MyActivityManager getActivityManager() {
        if (instance == null) {
            instance = new MyActivityManager();
        }
        return instance;
    }

    /**
     * 退出栈顶
     */
    public void removeActivityFromStack(Activity activity) {
        try {
            if (activity != null && activityStack != null && activityStack.contains(activity)) {
                activityStack.remove(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void popCurretentActivity() {
        Activity activity = currentActivity();
        if (activity == null) {
            return;
        }
        popActivity(activity);
    }


    /**
     * 获得当前栈顶
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (activityStack != null && !activityStack.empty()) {
            activity = activityStack.lastElement();
        }
        return activity;
    }

    /**
     * 当前Activity推入栈中
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 判断当前activity是否在栈中
     */
    public boolean isActivityContains(String className) {
        boolean ret = false;
        if (activityStack != null && !activityStack.empty()) {
            int size = activityStack.size();
            for (int i = size - 1; i >= 0; i--) {
                Activity ac = activityStack.elementAt(i);
                if (ac != null && ac.getClass().getSimpleName().equals(className)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * 退出所有Activity
     */
    public void popAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 退出所有Activity
     */
    public int getActivitySize() {
        int size = 0;

        if (activityStack != null && !activityStack.empty()) {
            size = activityStack.size();
        }

        return size;
    }

    public void reStartApp(Context context) {
        popAllActivity();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(launchIntent);
        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
