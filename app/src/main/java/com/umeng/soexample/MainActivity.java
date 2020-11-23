package com.umeng.soexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.umeng.soexample.api.ServiceApi;
import com.umeng.soexample.module.data.CityData;
import com.umeng.soexample.net.HttpManager;
import com.umeng.soexample.um.SharedActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShared = findViewById(R.id.btn_share);
        btnShared.setOnClickListener(this);
        /*Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);*/
        //initData();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_share:
                Intent intent = new Intent(MainActivity.this, SharedActivity.class);
                startActivity(intent);
                break;
        }
    }
}