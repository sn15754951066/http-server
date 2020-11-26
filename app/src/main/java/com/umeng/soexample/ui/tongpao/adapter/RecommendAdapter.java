package com.umeng.soexample.ui.tongpao.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseAdapter;
import com.umeng.soexample.module.data.tongpao.RecommendBean;
import com.umeng.soexample.ui.tongpao.bigimage.BigImageActivity;
import com.umeng.soexample.utils.DateUtils;
import com.umeng.soexample.utils.ImageLoader;
import com.umeng.soexample.utils.TxtUtils;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends BaseAdapter {

    public RecommendAdapter(Context context, List<RecommendBean.DataBean.PostDetailBean> list){
        super(context,list);
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_recommend_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        RecommendBean.DataBean.PostDetailBean bean = (RecommendBean.DataBean.PostDetailBean) data;
        ImageView imgHead = (ImageView) vh.getViewById(R.id.img_head);
        TextView txtName = (TextView) vh.getViewById(R.id.txt_name);
        TextView txtTime = (TextView) vh.getViewById(R.id.txt_time);
        NineGridImageView ninegrid = (NineGridImageView) vh.getViewById(R.id.nineGrid);
        ImageLoader.loadImage(bean.getHeadUrl(),imgHead);
        TxtUtils.setTextView(txtName,bean.getNickName());
        Long time = DateUtils.getDateToTime(bean.getCreateTime(),null);
        String date = DateUtils.getStandardDate(time);
        TxtUtils.setTextView(txtTime,date);
        ArrayList<String> imgs = new ArrayList<>();
        for(RecommendBean.DataBean.PostDetailBean.ImagesBean item:bean.getImages()){
            imgs.add(item.getFilePath());
        }
        ninegrid.setImagesData(imgs);
        ninegrid.setAdapter(new NineGridImageViewAdapter() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, Object o) {
                ImageLoader.loadImage((String) o,imageView);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List list) {
                //点击查看大图
                super.onItemImageClick(context, index, list);
                Intent intent = new Intent(context, BigImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("postion",index);
                bundle.putStringArrayList("urls",imgs);
                intent.putExtra("data",bundle);
                context.startActivity(intent);
            }
        });
    }
}
