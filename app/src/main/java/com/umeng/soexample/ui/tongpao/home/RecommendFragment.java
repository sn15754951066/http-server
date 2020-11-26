package com.umeng.soexample.ui.tongpao.home;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseAdapter;
import com.umeng.soexample.base.BaseFragment;
import com.umeng.soexample.interfaces.tongpao.IRecommend;
import com.umeng.soexample.module.data.tongpao.BannerBean;
import com.umeng.soexample.module.data.tongpao.DiscussedBean;
import com.umeng.soexample.module.data.tongpao.HotUserBean;
import com.umeng.soexample.module.data.tongpao.RecommendBean;
import com.umeng.soexample.persenter.tongpao.RecommendPersenter;
import com.umeng.soexample.ui.tongpao.adapter.DiscussedAdapter;
import com.umeng.soexample.ui.tongpao.adapter.RecommendAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecommendFragment extends BaseFragment<RecommendPersenter> implements IRecommend.View {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerview_talk)
    RecyclerView recyclerviewTalk;
    @BindView(R.id.recyclerview_recommend)
    RecyclerView recyclerviewRecommend;
    @BindView(R.id.recyclerview_hotuser)
    RecyclerView recyclerviewHotuser;

    //话题
    DiscussedAdapter discussedAdapter;
    List<DiscussedBean.DataBean> discussedList;

    //推荐列表数据
    RecommendAdapter recommendAdapter;
    List<RecommendBean.DataBean.PostDetailBean> recommendList;


    @Override
    public int getLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initView() {
        discussedList = new ArrayList<>();
        discussedAdapter = new DiscussedAdapter(getContext(),discussedList);
        recyclerviewTalk.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerviewTalk.setAdapter(discussedAdapter);
        discussedAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {

            }
        });

        recommendList = new ArrayList<>();
        recommendAdapter = new RecommendAdapter(mContext,recommendList);
        recyclerviewRecommend.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerviewRecommend.setAdapter(recommendAdapter);
    }

    @Override
    public RecommendPersenter createPersenter() {
        return new RecommendPersenter(this);
    }

    @Override
    public void initData() {
        persenter.getBanner();
        persenter.getRecommend();
        persenter.getDiscuessed();
    }

    @Override
    public void getRecommendReturn(RecommendBean result) {
        recommendList.clear();
        recommendList.add(result.getData().getPostDetail());
        recommendAdapter.notifyDataSetChanged();
    }

    @Override
    public void getBannerReturn(BannerBean result) {

    }

    @Override
    public void getDiscussedReturn(DiscussedBean result) {
        discussedList.clear();
        discussedList.addAll(result.getData());
        discussedAdapter.notifyDataSetChanged();
    }

    @Override
    public void getHotUserReturn(HotUserBean result) {

    }
}
