package com.umeng.soexample.module.home;

import com.umeng.soexample.api.ServiceApi;
import com.umeng.soexample.base.BaseModel;
import com.umeng.soexample.interfaces.Callback;
import com.umeng.soexample.interfaces.IHome;
import com.umeng.soexample.module.data.CityData;
import com.umeng.soexample.module.data.WeatherData;
import com.umeng.soexample.net.CommonSubscriber;
import com.umeng.soexample.net.HttpManager;
import com.umeng.soexample.utils.RxUtils;

import java.io.IOException;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Call;
import retrofit2.Response;

public class HomeModel extends BaseModel implements IHome.Model {
    @Override
    public void getCity(final Callback callback) {
        Disposable disposable = HttpManager.getInstance().getService().getCity()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<CityData>(callback) {
                    @Override
                    public void onNext(CityData cityData) {
                        callback.success(cityData);
                    }
                });  //产生一个网络请求disposable对象
        addDisposable(disposable);  //把请求对象添加到对象池
    }

    //获取天气的接口
    @Override
    public void getWeatcher(Map<String, String> map, Callback callback) {
        addDisposable(HttpManager.getInstance().getService().queryWeathcer(map)
        .compose(RxUtils.rxScheduler())
        .subscribeWith(new CommonSubscriber<WeatherData>(callback){

            @Override
            public void onNext(WeatherData weatherData) {
                if(weatherData.getStatus() == 200){
                    callback.success(weatherData);
                }else{
                    /*try{
                        String str = response.errorBody().string().toString();
                        callback.fail(str);
                    }catch (IOException e){
                        callback.fail(e.getMessage());
                    }*/
                }
            }
        }));
        /*api.queryWeathcer(map).enqueue(new retrofit2.Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {


            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                callback.fail(t.getMessage());
            }
        });*/
    }


}
