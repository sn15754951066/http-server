package com.umeng.soexample.interfaces.tongpao;

import com.umeng.soexample.interfaces.Callback;

/**
 * 提供给所有的业务使用的公用接口
 */
public interface IDown {

    interface DownModel{
        void downImage(String url,Callback callback);
    }

}
