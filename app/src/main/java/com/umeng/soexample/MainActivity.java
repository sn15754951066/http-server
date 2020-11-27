package com.umeng.soexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.umeng.soexample.api.ServiceApi;
import com.umeng.soexample.module.data.CityData;
import com.umeng.soexample.net.HttpManager;
import com.umeng.soexample.test.HandlerActivity;
import com.umeng.soexample.test.HttpActivity;
import com.umeng.soexample.ui.home.HomeActivity;
import com.umeng.soexample.ui.tongpao.TongpaoActivity;
import com.umeng.soexample.um.SharedActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnShared,btnTongpao,btnTest,btnHttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShared = findViewById(R.id.btn_share);
        btnTongpao = findViewById(R.id.btn_tongpao);
        btnTest = findViewById(R.id.btn_test);
        btnHttp = findViewById(R.id.btn_http);
        btnShared.setOnClickListener(this);
        btnTongpao.setOnClickListener(this);
        btnTest.setOnClickListener(this);
        btnHttp.setOnClickListener(this);
       /* Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_share:
                Intent intent = new Intent(MainActivity.this, SharedActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_tongpao:
                Intent intent1 = new Intent(MainActivity.this, TongpaoActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_test:
                Intent intent2 = new Intent(MainActivity.this, HandlerActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_http:
                Intent intent3 = new Intent(MainActivity.this, HttpActivity.class);
                startActivity(intent3);
                break;
        }
    }
}