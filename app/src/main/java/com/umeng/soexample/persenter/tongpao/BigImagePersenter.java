package com.umeng.soexample.persenter.tongpao;

import com.umeng.soexample.base.BasePersenter;
import com.umeng.soexample.interfaces.Callback;
import com.umeng.soexample.interfaces.tongpao.IBigImage;
import com.umeng.soexample.interfaces.tongpao.IDown;
import com.umeng.soexample.module.tongpao.BigImageModel;

public class BigImagePersenter extends BasePersenter<IBigImage.View> implements IBigImage.Persenter {

    IBigImage.View view;
    //接口的规范是DownModel mode
    BigImageModel mode;


    public BigImagePersenter(IBigImage.View view){
        this.view = view;
        mode = new BigImageModel();
    }

    @Override
    public void downImg(String url) {
        mode.downImage(url, new Callback() {
            @Override
            public void fail(String msg) {

            }

            @Override
            public void success(Object o) {
                if(view != null){
                    view.downReturn((String) o);
                }
            }
        });
    }

    @Override
    public void unAttachView() {
        super.unAttachView();
        if(mode != null){
            mode.clear();
            mode = null;
        }
    }
}
