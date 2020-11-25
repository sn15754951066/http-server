package com.umeng.soexample.ui.tongpao.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseAdapter;
import com.umeng.soexample.module.data.tongpao.DiscussedBean;
import com.umeng.soexample.utils.TxtUtils;

import java.util.List;

public class DiscussedAdapter extends BaseAdapter {

    public DiscussedAdapter(Context context, List<DiscussedBean.DataBean> data){
        super(context,data);
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_dicussed_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        DiscussedBean.DataBean dataBean = (DiscussedBean.DataBean) data;
        ImageView imgIcon = (ImageView) vh.getViewById(R.id.img_icon);
        TextView txtTag = (TextView) vh.getViewById(R.id.txt_tag);
        TxtUtils.setTextView(txtTag,dataBean.getName());
        //加载数据

    }
}
