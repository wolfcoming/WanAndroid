package com.czy.business_base;

import android.app.Application;
import android.content.Context;

import com.czy.business_base.launchstarter.TaskDispatcher;
import com.czy.business_base.launchstarter.utils.Utils;
import com.czy.business_base.net.interceptor.NetCacheInterceptor;
import com.czy.business_base.net.interceptor.OfflineCacheInterceptor;
import com.czy.business_base.net.interceptor.PublicHeaderAndParamInterceptor;
import com.czy.business_base.tasks.InitArouter;
import com.czy.business_base.tasks.InitBuglyTask;
import com.czy.business_base.tasks.InitDarkMode;
import com.czy.business_base.tasks.InitDataSave;
import com.czy.business_base.tasks.InitLogTask;
import com.czy.business_base.tasks.InitPushTask;
import com.czy.business_base.tasks.InitSmartRefresh;
import com.czy.lib_base.utils.ContentWrapperUtils;
import com.czy.lib_net.CommonApiService;
//import com.example.lib_ability.PushHelper;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;

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
                .addTask(new InitDataSave())
                .addTask(new InitDarkMode())
                .addTask(new InitPushTask())
                .addTask(new InitLogTask());
        initMouduleApplication();
        taskDispatcher.start();

        this.taskDispatcher.await();
        //初始化网络拦截器
        CommonApiService.Companion.getNetInterceptors().add(new NetCacheInterceptor());
        CommonApiService.Companion.getInterceptors().add(new OfflineCacheInterceptor());
        CommonApiService.Companion.getInterceptors().add(new PublicHeaderAndParamInterceptor());
        initPushSDK();
        initIM();
    }

    private void initIM() {

        // 初始化
//        NimUIKit.init(this, buildUIKitOptions());

        // IM 会话窗口的定制初始化。
//        SessionHelper.init();

        // 聊天室聊天窗口的定制初始化。
//        ChatRoomSessionHelper.init();

        // 通讯录列表定制初始化
//        ContactHelper.init();

//        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将进行自动登录）。不能对初始化语句添加进程判断逻辑。
//        String im_logininfo = DataSaveProxy.getInstance().getString("IM_LOGININFO", null);
//        LoginInfo loginInfo = new Gson().fromJson(im_logininfo,LoginInfo.class);
        NIMClient.init(this, null, null);
        if( Utils.isMainProcess(this)){
            NimUIKit.init(this);
        }
//        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
//                new Observer<StatusCode>() {
//                    public void onEvent(StatusCode status) {
//                        //获取状态的描述
//                        String desc = status.getDesc();
//                        HiLog.e(desc);
//                        if (status.wontAutoLogin()) {
//                            // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
//                        }
//                    }
//                }, true);
    }

    /**
     * 初始化推送SDK，在用户隐私政策协议同意后，再做初始化
     */
    private void initPushSDK() {


//            //建议在线程中执行初始化
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    PushHelper.init(getApplicationContext());
//                }
//            }).start();

    }

//    private UIKitOptions buildUIKitOptions() {
//        UIKitOptions options = new UIKitOptions();
//        // 设置app图片/音频/日志等缓存目录
////        options.appCacheDir = NimSDKOptionConfig.getAppCacheDir(this) + "/app";
//        return options;
//    }

}
