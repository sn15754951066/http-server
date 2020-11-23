package com.umeng.soexample.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseActivity;
import com.umeng.soexample.interfaces.IHome;
import com.umeng.soexample.module.data.CityData;
import com.umeng.soexample.module.data.WeatherData;
import com.umeng.soexample.persenter.home.HomePersenter;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends BaseActivity<HomePersenter> implements IHome.View {

    //接口类型
    IHome.Persenter homePersenter;
    Button btnWeather;
    TextView txtLoading;
    CityData cityData;


    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        btnWeather = findViewById(R.id.btn_weather);
        txtLoading = findViewById(R.id.txt_loading);
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

    @Override
    protected HomePersenter createPersenter() {
        return new HomePersenter(this);
    }

    @Override
    protected void initData() {
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