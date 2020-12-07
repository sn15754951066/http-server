package com.umeng.soexample.test;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import com.umeng.soexample.api.DwonApi;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class DownService extends Service {
    public String TAG = "DownService";

    public DownService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 不管是启动服务还是绑定服务,只要启动ok了,后续再启动或者绑定都不会再走
     * 这个方法,
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    /**
     * startService oncreate方法之后调用的方法,如果服务没有停掉,
     * 每次startService 都会走这个方法
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String path = intent.getStringExtra("path");
        Log.d(TAG, "onStartCommand: " + path);
        //下载代码
        download(path);
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("CheckResult")
    public void download(final String path) {
        OkHttpClient client = new OkHttpClient.Builder()
                .hostnameVerifier(new AllowAllHostnameVerifier())
                .connectTimeout(60000, TimeUnit.SECONDS)
                .writeTimeout(60000,TimeUnit.SECONDS)
                .build();

        new Retrofit.Builder()
                .baseUrl(DwonApi.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(DwonApi.class)
                .down()
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())//观察者运行的线程,如果不指定,直接使用被观察者运行的线程
                .subscribeWith(new ResourceSubscriber<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {

                        InputStream inputStream = responseBody.byteStream();
                        long max = responseBody.contentLength();
                        saveFile(path, inputStream, (int) max);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: "+t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveFile(String path, InputStream inputStream, int max) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            //当前下载的进度
            int count = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
                //count = count + len;
                count += len;
                //使用eventbus发送进度
                Log.d(TAG, "saveFile: " + count);
            }

            //下载完成
            fos.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            //下载失败
        }
    }

    /**
     * 停止服务stopService()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
