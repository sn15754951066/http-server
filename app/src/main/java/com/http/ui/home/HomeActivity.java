package com.http.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.http.R;
import com.http.interfaces.Callback;
import com.http.interfaces.IHome;
import com.http.module.data.CityData;
import com.http.module.data.HomeModel;
import com.http.persenter.home.HomePersenter;

public class HomeActivity extends AppCompatActivity implements IHome.View {

    //接口类型
    IHome.Persenter homePersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
    }

    private void initData() {
        homePersenter = new HomePersenter(this);
    }

    @Override
    public void getCityReturn(CityData result) {

    }

    @Override
    public void tips(String tip) {
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }
}