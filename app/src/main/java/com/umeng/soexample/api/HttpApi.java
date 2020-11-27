package com.umeng.soexample.api;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface HttpApi {

    String BASE_URL = "http://192.168.137.1:8080/";

    @GET("comein")
    Call<String> comeIn();

    @GET("comein_fail")
    Call<Response> comeInFail();

    @GET("pay")
    Call<Response> pay();


}
