package com.umeng.soexample.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.umeng.soexample.R;

import java.util.HashMap;
import java.util.Map;

public class HandlerActivity extends AppCompatActivity {
    private MyHandler handler = new MyHandler();

    TextView txtMsg,txtLine,txtImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        txtMsg = findViewById(R.id.txt_msg);
        txtLine = findViewById(R.id.txt_line);
        txtImg = findViewById(R.id.txt_img);

        initView();

        HashMap<String,Integer> map = new HashMap<>();
        map.put("1",1);

        testSpannable();

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



    private void testSpannable(){
        String word = "hello adroid";
        //1 spannable
        SpannableString spannableString = new SpannableString(word);
        spannableString.setSpan(new BackgroundColorSpan(Color.RED),0,5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        txtMsg.setText(spannableString);


        //登录注册的阅读链接
        String txt = "我已阅读《网络安全手册》";  //检索
        //起始位置
        int startPos = txt.indexOf("《");
        //结束位置
        int endPos = txt.lastIndexOf("》")+1;

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(txt);
        stringBuilder.setSpan(new ForegroundColorSpan(Color.BLUE),startPos,endPos,Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        //点击富文本
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //响应点击事件
                Log.i("TAG","onClick");
            }
        };

        stringBuilder.setSpan(clickableSpan,startPos,endPos,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        txtLine.setMovementMethod(LinkMovementMethod.getInstance());
        txtLine.setText(stringBuilder);

        String msg = "你好！[机器人](机器人)";
        //添加富文本的图片
        SpannableString imgSpannStr = new SpannableString(msg);
        //drawable <-> bitmap <-> resouce  图片格式的转换
        /*Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        bmp.setWidth(20);
        bmp.setHeight(20);*/
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0,0,20,20);
        //imageSpan
        ImageSpan imageSpan = new ImageSpan(drawable);
        int start = msg.indexOf("[");
        int end = msg.lastIndexOf("]")+1;
        imgSpannStr.setSpan(imageSpan,start,end,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        txtImg.setText(imgSpannStr);

    }




}