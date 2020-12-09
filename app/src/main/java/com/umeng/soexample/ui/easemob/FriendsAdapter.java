package com.umeng.soexample.ui.easemob;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseAdapter;
import com.umeng.soexample.module.data.EMUserInfo;

import java.util.List;

public class FriendsAdapter extends BaseAdapter {
    public FriendsAdapter(Context context, List<EMUserInfo> list){
        super(context,list);
    }
    @Override
    protected int getLayout() {
        return R.layout.layout_friend_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        EMUserInfo _data = (EMUserInfo) data;
        TextView txtUserName = (TextView) vh.getViewById(R.id.txt_username);
        if(!TextUtils.isEmpty(_data.getNickname())){
            txtUserName.setText(_data.getNickname());
        }else{
            txtUserName.setText(_data.getUid());
        }
        ImageView imgheader = (ImageView) vh.getViewById(R.id.img_header);
        String header = _data.getHeader();
        if(!TextUtils.isEmpty(header)){
            Glide.with(imgheader).load(header).into(imgheader);
        }

        Button btnOpenDetail = (Button) vh.getViewById(R.id.btn_openUserDetail);
        btnOpenDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iItemViewClick != null){
                    iItemViewClick.itemViewClick(v.getId(),data);
                }

            }
        });
    }
}
