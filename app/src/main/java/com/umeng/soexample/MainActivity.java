package com.umeng.soexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.umeng.soexample.api.ServiceApi;
import com.umeng.soexample.module.data.CityData;
import com.umeng.soexample.net.HttpManager;
import com.umeng.soexample.test.CoordinatorLayoutActivity;
import com.umeng.soexample.test.HandlerActivity;
import com.umeng.soexample.test.HttpActivity;
import com.umeng.soexample.test.RefreshListActivity;
import com.umeng.soexample.test.TestJPushActivity;
import com.umeng.soexample.ui.home.HomeActivity;
import com.umeng.soexample.ui.map.MapActivity;
import com.umeng.soexample.ui.tongpao.TongpaoActivity;
import com.umeng.soexample.um.SharedActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnShared,btnTongpao,btnTest,btnHttp,btnCoordinator,btnMyList,btnJpush,btnMap;

    NavigationView navView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        btnShared = findViewById(R.id.btn_share);
        btnTongpao = findViewById(R.id.btn_tongpao);
        btnTest = findViewById(R.id.btn_test);
        btnHttp = findViewById(R.id.btn_http);
        btnMyList = findViewById(R.id.btn_myList);
        btnCoordinator = findViewById(R.id.btn_coordinator);
        btnJpush = findViewById(R.id.btn_jpush);
        btnMap = findViewById(R.id.btn_map);
        btnShared.setOnClickListener(this);
        btnTongpao.setOnClickListener(this);
        btnTest.setOnClickListener(this);
        btnHttp.setOnClickListener(this);
        btnCoordinator.setOnClickListener(this);
        btnMyList.setOnClickListener(this);
        btnJpush.setOnClickListener(this);
        btnMap.setOnClickListener(this);
       /* Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);*/
    }

    /**
    private void initNavi(){

    }

    /**
     * 创建菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer_main,menu);
        return true;
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
            case R.id.btn_coordinator:
                Intent intent4 = new Intent(MainActivity.this, CoordinatorLayoutActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_myList:
                Intent intent5 = new Intent(MainActivity.this, RefreshListActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_jpush:
                Intent intent6 = new Intent(MainActivity.this, TestJPushActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_map:
                Intent intent7 = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent7);
                break;
        }
    }
}