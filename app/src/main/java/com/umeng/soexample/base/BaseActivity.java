package com.umeng.soexample.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.umeng.soexample.interfaces.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<P extends BasePersenter> extends AppCompatActivity implements IBaseView {


    //p层关联
    protected P persenter;
    Unbinder unbinder; //butterknife


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //需要界面view
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
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
        if(unbinder != null){
            unbinder.unbind();
        }
        //释放p关联的v的引用
        if(persenter != null){
            persenter.unAttachView();
        }
    }


}
