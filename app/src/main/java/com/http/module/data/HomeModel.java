package com.http.module.data;

import com.http.api.ServiceApi;
import com.http.interfaces.Callback;
import com.http.interfaces.IHome;
import com.http.net.HttpManager;

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
}
