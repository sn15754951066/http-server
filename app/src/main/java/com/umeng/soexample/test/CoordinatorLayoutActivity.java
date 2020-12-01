package com.umeng.soexample.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseAdapter;
import com.umeng.soexample.module.data.tongpao.DiscussedBean;
import com.umeng.soexample.utils.TxtUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorLayoutActivity extends AppCompatActivity {

    private List<String> list;
    private MyAdapter myAdapter;

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        recyclerView = findViewById(R.id.recyclerView);
        initView();




    }

    private void initView(){

        list = new ArrayList<>();
        for(int i=0;i<100; i++){
            list.add("item"+i);
        }
        myAdapter = new MyAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

    }

    class MyAdapter extends BaseAdapter{
        public MyAdapter(Context context, List<String> data){
            super(context,data);
        }

        @Override
        protected int getLayout() {
            return R.layout.layout_coordinator_item;
        }

        @Override
        protected void bindData(Object data, VH vh) {
            TextView txtInfo = (TextView)(vh.getViewById(R.id.txt_info));
            //加载数据
            txtInfo.setText(String.valueOf(data));
        }
    }
}