package com.http.persenter.home;

import com.http.interfaces.Callback;
import com.http.interfaces.IHome;
import com.http.module.data.CityData;
import com.http.module.data.HomeModel;

public class HomePersenter implements IHome.Persenter {

    IHome.View view;
    IHome.Model model;


    public HomePersenter(IHome.View view){
        this.view = view;
        this.model = new HomeModel();
    }

    @Override
    public void getCity() {
        this.model.getCity(new Callback() {
            @Override
            public void fail(String msg) {
                if(view != null){
                    view.tips(msg);
                }
            }

            @Override
            public void success(Object o) {
                if(view != null){
                    view.getCityReturn((CityData) o);
                }
            }
        });
    }
}
