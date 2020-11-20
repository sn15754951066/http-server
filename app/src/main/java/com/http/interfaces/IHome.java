package com.http.interfaces;

import com.http.module.data.CityData;

public interface IHome {

    //home业务下的 v层接口
    interface View extends BaseView{
        void getCityReturn(CityData result);
    }

    //home业务下 P层接口
    interface Persenter{
        void getCity();
    }

    //home业务下的model
    interface Model{
        void getCity(Callback callback);
    }

}
