package com.umeng.soexample.api;

import com.umeng.soexample.module.data.tongpao.RecommendBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface TongpaoApi {
    String BASE_URL = "http://cdwan.cn:7000/tongpao/";

    @GET("home/recommend.json")
    Flowable<RecommendBean> getRecommend();

}
