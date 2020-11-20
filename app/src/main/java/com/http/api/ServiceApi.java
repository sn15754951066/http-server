package com.http.api;

import com.http.module.data.CityData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceApi {
    String BASE_URL = "https://jisutqybmf.market.alicloudapi.com/weather/";

    @GET("city")
    Call<CityData> getCity();


}
