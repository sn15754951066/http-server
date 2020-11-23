package com.umeng.soexample.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.umeng.soexample.R;

public class HandlerActivity extends AppCompatActivity {
    private MyHandler handler = new MyHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        initView();
    }

    private void initView() {
        Message msg1 = new Message();
        msg1.what = 2;
        msg1.obj = "message";
        handler.sendMessageAtTime(msg1,1000);
        //存储消息内存的对象
        Message msg = new Message();
        msg.what = 1;
        msg.obj = "hello";
        //发送消息
        handler.sendMessage(msg);

    }

    class MyHandler extends Handler{
        //接收处理消息
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }




}