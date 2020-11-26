package com.umeng.soexample.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.umeng.soexample.app.MyApp;

public class ImageLoader {

    //url 图片地址
    public static void loadImage(String url, ImageView img){
        //用key为image的值的时候来判断当前时无图还有有图模式
        if(SpUtils.getInstance().getBoolean("image") && img != null){
            Glide.with(MyApp.app).load(url).into(img);
        }
    }
}
