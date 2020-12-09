package com.umeng.soexample.ui.easemob;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseAdapter;

import java.util.List;

public class FriendsAdapter extends BaseAdapter {
    public FriendsAdapter(Context context, List<String> list){
        super(context,list);
    }
    @Override
    protected int getLayout() {
        return R.layout.layout_friend_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        TextView txtUserName = (TextView) vh.getViewById(R.id.txt_username);
        txtUserName.setText(data.toString());
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
