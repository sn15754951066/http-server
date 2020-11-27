package com.umeng.soexample.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.umeng.soexample.app.Constants;
import com.umeng.soexample.app.MyApp;

public class ImageLoader {

    //url 图片地址
    public static void loadImage(String url, ImageView img){
        //用key为image的值的时候来判断当前时无图还有有图模式
        if(SpUtils.getInstance().getBoolean("image") && img != null){
            Glide.with(MyApp.app).load(url).into(img);
        }
    }

    /**
     * 解析图片的路径
     * @param url
     * @return
     */
    public static String[] splitUrl(String url){
        String[] arr = new String[3];
        int end = url.lastIndexOf("/")+1;
        String baseUrl = url.substring(0,end);
        String imgName = url.substring(end,url.length());
        String path = Constants.PATH_IMGS+"/"+imgName;
        arr[0] = baseUrl;
        arr[1] = imgName;
        arr[2] = path;
        return arr;
    }

}
