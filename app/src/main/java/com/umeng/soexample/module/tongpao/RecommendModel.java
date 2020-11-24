package com.umeng.soexample.module.tongpao;

import com.umeng.soexample.base.BaseModel;
import com.umeng.soexample.interfaces.Callback;
import com.umeng.soexample.interfaces.tongpao.IRecommend;
import com.umeng.soexample.module.data.tongpao.RecommendBean;
import com.umeng.soexample.net.CommonSubscriber;
import com.umeng.soexample.net.HttpManager;
import com.umeng.soexample.utils.RxUtils;

public class RecommendModel extends BaseModel implements IRecommend.Model {


    @Override
    public void loadRecommend(Callback callback) {
        addDisposable(
                HttpManager.getInstance().getTongpaoApi().getRecommend()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<RecommendBean>(callback){

                    @Override
                    public void onNext(RecommendBean recommendBean) {
                        callback.success(recommendBean);
                    }
                })
        );
    }
}
