package com.umeng.soexample.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.umeng.soexample.utils.PrintUtils;

public class MyVG extends LinearLayout {
    private static String TAG = MyVG.class.getSimpleName();
    public MyVG(Context context) {
        super(context);
    }

    public MyVG(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVG(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        PrintUtils.printInfo(TAG,"dispatchTouchEvent",ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    //事件拦截
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        PrintUtils.printInfo(TAG,"onInterceptHoverEvent",event.getAction());
        return super.onInterceptHoverEvent(event);
    }

    //事件响应
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PrintUtils.printInfo(TAG,"onTouchEvent",event.getAction());
        //return true;
        return super.onTouchEvent(event);
    }
}
