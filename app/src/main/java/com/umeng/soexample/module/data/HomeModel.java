package com.umeng.soexample.module.data;

import com.umeng.soexample.api.ServiceApi;
import com.umeng.soexample.interfaces.Callback;
import com.umeng.soexample.interfaces.IHome;
import com.umeng.soexample.net.HttpManager;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class HomeModel implements IHome.Model {
    @Override
    public void getCity(final Callback callback) {
        ServiceApi api = HttpManager.getInstance().getService();
        api.getCity().enqueue(new retrofit2.Callback<CityData>() {
            @Override
            public void onResponse(Call<CityData> call, Response<CityData> response) {
                callback.success(response.body());
            }

            @Override
            public void onFailure(Call<CityData> call, Throwable t) {
                callback.fail(t.getMessage());
            }
        });
    }

    //获取天气的接口
    @Override
    public void getWeatcher(Map<String, String> map, Callback callback) {
        ServiceApi api = HttpManager.getInstance().getService();
        api.queryWeathcer(map).enqueue(new retrofit2.Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if(response.code() == 200){
                    callback.success(response.body());
                }else{
                    try{
                        String str = response.errorBody().string().toString();
                        callback.fail(str);
                    }catch (IOException e){
                        callback.fail(e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                callback.fail(t.getMessage());
            }
        });
    }
}
