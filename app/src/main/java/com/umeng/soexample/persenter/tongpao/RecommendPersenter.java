package com.umeng.soexample.persenter.tongpao;

import com.umeng.soexample.base.BasePersenter;
import com.umeng.soexample.interfaces.Callback;
import com.umeng.soexample.interfaces.tongpao.IRecommend;
import com.umeng.soexample.module.data.tongpao.RecommendBean;
import com.umeng.soexample.module.tongpao.RecommendModel;

public class RecommendPersenter extends BasePersenter<IRecommend.View> implements IRecommend.Persenter {

    IRecommend.View view;
    IRecommend.Model model;

    public RecommendPersenter(IRecommend.View view){
        this.view = view;
        this.model = new RecommendModel();
    }

    @Override
    public void getRecommend() {
        this.model.loadRecommend(new Callback() {
            @Override
            public void fail(String msg) {
                if(view != null){
                    view.tips(msg);
                }
            }

            @Override
            public void success(Object o) {
                if(view != null){
                    view.getRecommendReturn((RecommendBean) o);
                }
            }
        });
    }

}
