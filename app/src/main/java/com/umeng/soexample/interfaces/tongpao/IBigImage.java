package com.umeng.soexample.interfaces.tongpao;

import com.umeng.soexample.interfaces.Callback;
import com.umeng.soexample.interfaces.IBasePersenter;
import com.umeng.soexample.interfaces.IBaseView;
import com.umeng.soexample.interfaces.IModel;

public interface IBigImage {

    interface View extends IBaseView{

        void downReturn(String path);

    }

    interface Persenter extends IBasePersenter<View>{
        void downImg(String url);
    }


    interface Model extends IModel{
        void getBigImage();
    }
}
