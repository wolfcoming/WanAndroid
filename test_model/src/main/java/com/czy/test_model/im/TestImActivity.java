package com.czy.test_model.im;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.czy.business_base.BaseActivity;
import com.czy.business_base.dataSave.DataSaveProxy;
import com.czy.lib_log.HiLog;
import com.czy.test_model.R;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

/**
 * @author yangqing
 * @time 5/23/21 1:34 PM
 * @describe
 */
public class TestImActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_im;
    }

    @Override
    public void initView() {

        msgListener();
        EditText name = findViewById(R.id.etName);
        EditText pwd = findViewById(R.id.etPwd);
        EditText etToAccout = findViewById(R.id.etTo);

        findViewById(R.id.btnLogin).setOnClickListener(v->{
            LoginInfo loginInfo = new LoginInfo(name.getText().toString(),pwd.getText().toString());
            NIMClient.getService(AuthService.class).login(loginInfo)
                    .setCallback(new RequestCallback() {
                        @Override
                        public void onSuccess(Object param) {
                            Toast.makeText(TestImActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            HiLog.e("登陆成功");
                        }

                        @Override
                        public void onFailed(int code) {
                            Toast.makeText(TestImActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                            HiLog.e("登陆失败");
                        }

                        @Override
                        public void onException(Throwable exception) {
                            Toast.makeText(TestImActivity.this, "登陆发生异常", Toast.LENGTH_SHORT).show();
                            HiLog.e("登陆发生异常");
                        }
                    });
        });


        findViewById(R.id.btnSendMsg).setOnClickListener(v -> {
            // 该帐号为示例
            String account = etToAccout.getText().toString();
            // 以单聊类型为例
            SessionTypeEnum sessionType = SessionTypeEnum.P2P;
            String text = "this is an example";
            // 创建一个文本消息
            IMMessage textMessage = MessageBuilder.createTextMessage(account, sessionType, text);
            // 发送给对方
            NIMClient.getService(MsgService.class).sendMessage(textMessage, false).setCallback(new RequestCallback<Void>() {
                @Override
                public void onSuccess(Void param) {
                    HiLog.e("发送消息成功");
                }

                @Override
                public void onFailed(int code) {
                    HiLog.e("发送消息失败");
                }

                @Override
                public void onException(Throwable exception) {
                    HiLog.e("发送消息异常了");
                }
            });
        });
    }

    private void msgListener() {
        Observer<List<IMMessage>> incomingMessageObserver =
                (Observer<List<IMMessage>>) messages -> {
                    Toast.makeText(this, "接收到了消息", Toast.LENGTH_SHORT).show();
                    // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                    for (IMMessage message : messages) {
                        String content = message.getContent();
                        HiLog.e(content);
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);

    }

    @Override
    public void initData() {

    }
}
