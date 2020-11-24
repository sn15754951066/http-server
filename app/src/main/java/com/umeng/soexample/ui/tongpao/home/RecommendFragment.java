package com.umeng.soexample.ui.tongpao.home;

import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseFragment;
import com.umeng.soexample.base.BasePersenter;
import com.umeng.soexample.interfaces.tongpao.IRecommend;
import com.umeng.soexample.module.data.tongpao.RecommendBean;
import com.umeng.soexample.persenter.tongpao.RecommendPersenter;

public class RecommendFragment extends BaseFragment<RecommendPersenter> implements IRecommend.View {
    @Override
    public int getLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView() {

    }

    @Override
    public RecommendPersenter createPersenter() {
        return new RecommendPersenter(this);
    }

    @Override
    public void initData() {
        persenter.getRecommend();
    }

    @Override
    public void getRecommendReturn(RecommendBean result) {

    }
}
