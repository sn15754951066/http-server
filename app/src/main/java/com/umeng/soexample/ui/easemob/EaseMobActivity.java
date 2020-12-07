package com.umeng.soexample.ui.easemob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.umeng.soexample.R;

public class EaseMobActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = EaseMobActivity.class.getSimpleName();

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ease_mob);

        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login(){
    //登录
        String username = "2002a_1";
        String password = "123456";
        Log.i(TAG,"环信login");
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.i(TAG,"登录成功");
            }

            @Override
            public void onError(int code, String error) {
                Log.i(TAG,"onError:"+error);
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.i(TAG,"status:"+status);
            }
        });
    }
}