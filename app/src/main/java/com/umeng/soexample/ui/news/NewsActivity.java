package com.umeng.soexample.ui.news;

import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseActivity;
import com.umeng.soexample.base.BasePersenter;
import com.umeng.soexample.interfaces.IBasePersenter;
import com.umeng.soexample.persenter.news.NewsPersenter;

public class NewsActivity extends BaseActivity<NewsPersenter> {
    @Override
    protected int getLayout() {
        return R.layout.activity_news;
    }

    //找控件
    @Override
    protected void initView() {

    }

    @Override
    protected NewsPersenter createPersenter() {
        return new NewsPersenter();
    }

    //读取intent中的数据获取网络请求的触发
    @Override
    protected void initData() {
        persenter.getNews();
    }




}
