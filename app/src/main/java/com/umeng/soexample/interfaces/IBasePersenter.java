package com.umeng.soexample.interfaces;

public interface IBasePersenter<V extends BaseView> {

    void attachView(V view);

    void unAttachView();


}
