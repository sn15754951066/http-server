package com.umeng.soexample.api;

import com.umeng.soexample.module.data.tongpao.BannerBean;
import com.umeng.soexample.module.data.tongpao.DiscussedBean;
import com.umeng.soexample.module.data.tongpao.HotUserBean;
import com.umeng.soexample.module.data.tongpao.RecommendBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface TongpaoApi {
    String BASE_URL = "http://cdwan.cn:7000/tongpao/";

    @GET("home/recommend.json")
    Flowable<RecommendBean> getRecommend();

    @GET("home/banner.json")
    Flowable<BannerBean> getBanner();

    @GET("home/topic_discussed.json")
    Flowable<DiscussedBean> getDiscussed();

    @GET("home/hot_user.json")
    Flowable<HotUserBean> getHotUser();

}
