package com.umeng.soexample.test;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.umeng.soexample.utils.PrintUtils;

public class MyViewGroup extends ViewGroup {

    private static String TAG = MyViewGroup.class.getSimpleName();

    public MyViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //super.onLayout(changed,l,t,r,b);
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
        return super.onTouchEvent(event);
    }
}
