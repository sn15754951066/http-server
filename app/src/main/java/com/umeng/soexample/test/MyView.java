package com.umeng.soexample.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.umeng.soexample.utils.PrintUtils;

public class MyView extends View {
    private static String TAG = MyView.class.getSimpleName();
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        PrintUtils.printInfo(TAG,"dispatchTouchEvent",event.getAction());
        return super.dispatchTouchEvent(event);
    }

    //事件响应
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PrintUtils.printInfo(TAG,"onTouchEvent",event.getAction());
        //return super.onTouchEvent(event);
        return true;   //事件将由view来消费
    }
}
