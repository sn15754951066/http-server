package com.umeng.soexample.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.umeng.soexample.app.Constants;
import com.umeng.soexample.net.HttpManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 下载服务
 */
public class DownService extends Service {

    private static String TAG = DownService.class.getSimpleName();
    private MyBinder myBinder;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        //main
        Log.d(TAG, "onCreate:"+Thread.currentThread().getName());
        myBinder = new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    class MyBinder extends Binder{

        /**
         * 下载图片
         * @param url
         */
        public void downImg(String url){
           /* @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask<String, Long, Void>() {
                //耗时操作的方法
                @Override
                protected Void doInBackground(String... voids) {
                    //第一步取基础地址
                    if(voids.length > 0){
                        //https://tpcdn.whfpsoft.com:443/File/post/20200731/82a22843569d90ad5e87090f151d75d5.jpg
                        String imgUrl = voids[0]; //获取不定参数
                        int end = imgUrl.lastIndexOf("/");
                        String baseUrl = imgUrl.substring(0,end+1);  //基础地址
                        String name = imgUrl.substring(end+1,imgUrl.length()); //图片文件的名字
                        final FileOutputStream fileOutputStream;
                        final InputStream inputStream;
                        Call<ResponseBody> call = HttpManager.getInstance().getImageApi(baseUrl).downImage(name);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                //读取文件流
                                inputStream = response.body().byteStream();
                                try {
                                    //判断当前的流是否有数据
                                    if(inputStream.available() > 0){
                                        //判断当前本地的路径是否存在
                                        File file = new File(Constants.PATH_IMGS);
                                        if(file.isDirectory() && !file.exists()){
                                            boolean bool = file.createNewFile();
                                            if(bool){
                                                String imgPath = Constants.PATH_IMGS+"/"+name;  //本地保存图片的路径
                                                fileOutputStream = new FileOutputStream(imgPath);
                                                int n = 0;
                                                byte[] bytes = new byte[4096];
                                                while((n=inputStream.read(bytes)) != -1){
                                                    fileOutputStream.write(bytes);
                                                }
                                                fileOutputStream.flush(); //刷新到sd卡
                                            }else{
                                                Log.i(TAG,"创建失败");
                                            }
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }finally {
                                    try {
                                        inputStream.close();
                                        fileOutputStream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.i(TAG,"onFailure:"+t.getMessage());
                            }
                        });
                    }
                    return null;
                }
            };
            asyncTask.execute(url);*/
        }

    }
}
