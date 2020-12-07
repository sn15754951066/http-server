package com.umeng.soexample.ui.easemob;

import android.content.Context;
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
    }
}
