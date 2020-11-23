package com.umeng.soexample.base;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.umeng.soexample.interfaces.BaseView;


public abstract class BaseActivity<P extends BasePersenter> extends AppCompatActivity implements BaseView {


    //p层关联
    protected P persenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //需要界面view
        setContentView(getLayout());
        //初始化界面
        initView();
        persenter = createPersenter();
        if(persenter != null){
            persenter.attachView(this);
        }
        //初始化界面数据
        initData();

    }




    //定义一个获取当前界面的方法  由子类提供的
    protected abstract int getLayout();
    //初始化界面
    protected abstract void initView();
    //初始化p层的方法
    protected abstract P createPersenter();
    //初始化界面数据
    protected abstract void initData();


    @Override
    public void tips(String tip) {

    }

    @Override
    public void loading(int visible) {

    }

    /**
     * 界面销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
