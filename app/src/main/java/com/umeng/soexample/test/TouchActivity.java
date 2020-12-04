package com.umeng.soexample.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

import com.umeng.soexample.R;
import com.umeng.soexample.utils.PrintUtils;

public class TouchActivity extends AppCompatActivity {

    private static final String TAG = TouchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
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