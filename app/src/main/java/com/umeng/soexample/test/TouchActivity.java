package com.umeng.soexample.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.umeng.soexample.R;
import com.umeng.soexample.utils.PrintUtils;

import java.io.File;

/**
 * 事件分发步骤
 * 事件状态 down move up cancel
 * 事件分发流程中的方法 dispathTouchEvent   onInterceptTouchEvent(只存在于ViewGroup)  onTouchEvent
 *
 * 1.找事件的消费对象-捕获
 * 2.move/up找事件消费对象汇报情况
 * 3.捕获（由外向内）
 * 4.确定事件的最终消费者 （由内向外）
 * 5.move/up在上一步的结果基础上，（由外向内）传递事件到事件的消费对象
 *
 *
 */
public class TouchActivity extends AppCompatActivity {

    private static final String TAG = TouchActivity.class.getSimpleName();

    Button btnDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        btnDown = findViewById(R.id.btn_down);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down();
            }
        });
    }

    private void down(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DownService downService = new DownService();
                downService.download(Environment.getStorageDirectory()+ File.separator+"aac"+File.separator+"test.apk");
            }
        }).start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        PrintUtils.printInfo(TAG,"dispatchTouchEvent",ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PrintUtils.printInfo(TAG,"onTouchEvent",event.getAction());
        return super.onTouchEvent(event);
    }
}