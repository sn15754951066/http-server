package com.http.interfaces;

import com.http.module.data.CityData;
import com.http.module.data.WeatherData;

import java.util.Map;

public interface IHome {

    //home业务下的 v层接口
    interface View extends BaseView{
        //获取城市数据返回
        void getCityReturn(CityData result);
        //获取天气数据返回
        void getWeatherReturn(WeatherData result);
    }

    //home业务下 P层接口
    interface Persenter{
        void getCity();
        //获取天气数据--> v层的列表选择
        void getWeather(Map<String,String> map);
    }

    //home业务下的model
    interface Model{
        void getCity(Callback callback);

        //获取天气数据 --> p层调用m层的接口
        void getWeatcher(Map<String,String> map,Callback callback);
    }

}
