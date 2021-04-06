package com.czy.business_base.tasks

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.czy.business_base.CrashHandler
import com.czy.business_base.R
import com.czy.business_base.launchstarter.task.Task
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.UpgradeInfo
import com.tencent.bugly.beta.ui.UILifecycleListener


class InitBuglyTask :Task() {
    override fun run() {

        //bugly
//        自动初始化开关
        Beta.autoInit = true
//        自动检查更新开关
        Beta.autoCheckUpgrade = true
        //升级检查周期设置 60s内SDK不重复向后台请求策略
        Beta.upgradeCheckPeriod = 60 * 1000;
        //设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度
        Beta.initDelay = 1 * 1000;
        //设置通知栏大图标
        Beta.largeIconId = R.mipmap.leak_canary_icon;
        //设置通知栏小图标
        Beta.smallIconId = R.mipmap.leak_canary_icon;
        //设置点击过确认的弹窗在App下次启动自动检查更新时会再次显示。
        Beta.showInterruptedStrategy = true;
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;

        Beta.upgradeDialogLifecycleListener =
            object : UILifecycleListener<UpgradeInfo?> {
                var TAG = "upgradeDialogLifecycleListener"
                override fun onCreate(
                    context: Context?,
                    view: View?,
                    upgradeInfo: UpgradeInfo?
                ) {
                    Log.d(TAG, "onCreate")
                }

                override fun onStart(
                    context: Context?,
                    view: View?,
                    upgradeInfo: UpgradeInfo?
                ) {
                    Log.d(TAG, "onStart")
                }

                override fun onResume(
                    context: Context?,
                    view: View,
                    upgradeInfo: UpgradeInfo?
                ) {
                    Log.d(TAG, "onResume")
                    // 注：可通过这个回调方式获取布局的控件，如果设置了id，可通过findViewById方式获取，如果设置了tag，可以通过findViewWithTag，具体参考下面例子:

                    // 通过id方式获取控件，并更改imageview图片
//                    val imageView: ImageView = view.findViewById(R.id.icon) as ImageView
//                    imageView.setImageResource(R.mipmap.ic_launcher)
//
//                    // 通过tag方式获取控件，并更改布局内容
//                    val textView: TextView = view.findViewWithTag("textview") as TextView
//                    textView.setText("my custom text")
//
//                    // 更多的操作：比如设置控件的点击事件
//                    imageView.setOnClickListener(object : View.OnClickListener() {
//                        fun onClick(v: View?) {
//                            val intent =
//                                Intent(getApplicationContext(), OtherActivity::class.java)
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            startActivity(intent)
//                        }
//                    })
                }

                override fun onPause(
                    context: Context?,
                    view: View?,
                    upgradeInfo: UpgradeInfo?
                ) {
                    Log.d(TAG, "onPause")
                }

                override fun onStop(
                    context: Context?,
                    view: View?,
                    upgradeInfo: UpgradeInfo?
                ) {
                    Log.d(TAG, "onStop")
                }

                override fun onDestroy(
                    context: Context?,
                    view: View?,
                    upgradeInfo: UpgradeInfo?
                ) {
                    Log.d(TAG, "onDestory")
                }
            }
        Bugly.init(mContext, "e049243189", false)
//        CrashReport.initCrashReport(mContext, "e049243189", true);
        CrashHandler.getCrashHander().init(mContext)
    }
}