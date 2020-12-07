package com.umeng.soexample.api;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface DwonApi {
    String BASE_URL = "https://mptest.yanshanjiance.cn/api/app/";

    @GET("DownloadLatestApp?tag=8")
    Flowable<ResponseBody> down();

}
