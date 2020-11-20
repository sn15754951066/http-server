package com.http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.http.api.ServiceApi;
import com.http.module.data.CityData;
import com.http.net.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }

    private void initData() {
        ServiceApi serviceApi = HttpManager.getInstance().getService();
        Call<CityData> call = serviceApi.getCity();
        call.enqueue(new Callback<CityData>() {
            @Override
            public void onResponse(Call<CityData> call, Response<CityData> response) {

            }

            @Override
            public void onFailure(Call<CityData> call, Throwable t) {

            }
        });
    }
}