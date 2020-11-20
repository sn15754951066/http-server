package com.http.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.http.R;
import com.http.interfaces.Callback;
import com.http.interfaces.IHome;
import com.http.module.data.CityData;
import com.http.module.data.HomeModel;
import com.http.module.data.WeatherData;
import com.http.persenter.home.HomePersenter;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements IHome.View {

    //接口类型
    IHome.Persenter homePersenter;
    Button btnWeather;
    TextView txtLoading;
    CityData cityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnWeather = findViewById(R.id.btn_weather);
        txtLoading = findViewById(R.id.txt_loading);
        initData();
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cityData != null){
                    Map<String,String> map = new HashMap<>();
                    map.put("cityid", String.valueOf(cityData.getResult().get(3).getCityid()));
                    homePersenter.getWeather(map);
                }
            }
        });
    }

    private void initData() {
        homePersenter = new HomePersenter(this);
        homePersenter.getCity();
    }

    @Override
    public void getCityReturn(CityData result) {
        cityData = result;
    }

    //获取天气数据的返回
    @Override
    public void getWeatherReturn(WeatherData result) {

    }

    @Override
    public void tips(String tip) {
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loading(int visible) {

    }
}