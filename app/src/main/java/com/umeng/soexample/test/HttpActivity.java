package com.umeng.soexample.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseActivity;
import com.umeng.soexample.base.BasePersenter;
import com.umeng.soexample.net.HttpManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpActivity extends BaseActivity {
    @BindView(R.id.input_ip)
    EditText inputIp;
    @BindView(R.id.btn_comeIn)
    Button btnComeIn;
    @BindView(R.id.btn_comeInFail)
    Button btnComeInFail;

    @Override
    protected int getLayout() {
        return R.layout.activity_http;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePersenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_comeIn, R.id.btn_comeInFail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_comeIn:
                comeIn();
                break;
            case R.id.btn_comeInFail:
                comeInFail();
                break;
        }
    }

    private void comeIn(){
        HttpManager.getInstance().getHttpApi().comeIn().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("TAG","onResponse");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("TAG","onError");
            }
        });
    }

    private void comeInFail(){
        HttpManager.getInstance().getHttpApi().comeInFail().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {
                Log.i("TAG","onResponse");
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.i("TAG","onError");
            }
        });
    }

}
