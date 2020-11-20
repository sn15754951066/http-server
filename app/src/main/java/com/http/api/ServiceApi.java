package com.http.api;

import com.http.module.data.CityData;
import com.http.module.data.WeatherData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

public interface ServiceApi {
    String BASE_URL = "https://jisutqybmf.market.alicloudapi.com/weather/";

    @GET("city")
    Call<CityData> getCity();

    @GET("query")
    Call<WeatherData> queryWeathcer(@QueryMap Map<String,String> map);


}
