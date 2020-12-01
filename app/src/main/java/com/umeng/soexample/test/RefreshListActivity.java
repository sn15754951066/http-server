package com.umeng.soexample.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.soexample.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RefreshListActivity extends AppCompatActivity implements View.OnClickListener{


    MyRefreshList myRefreshList;

    RecyclerView recyclerView;

    TextView txtShowHeader,txtShowFooter,txtHideHeader,txtHideFooter,title;

    MyAdapter myAdapter;
    List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);

        myRefreshList = findViewById(R.id.myFreshList);
        recyclerView = findViewById(R.id.recyclerView);

        txtShowHeader = findViewById(R.id.txt_showHeader);
        txtShowFooter = findViewById(R.id.txt_showFooter);
        txtHideHeader = findViewById(R.id.txt_hideHeader);
        txtHideFooter = findViewById(R.id.txt_hideFooter);
       // title = findViewById(R.id.txt_title);

        txtShowHeader.setOnClickListener(this);
        txtShowFooter.setOnClickListener(this);
        txtHideHeader.setOnClickListener(this);
        txtHideFooter.setOnClickListener(this);

        myRefreshList.init();


        list = new ArrayList<>();
        for(int i=0; i<100; i++){
            list.add("item"+i);
        }
        myAdapter = new MyAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_showHeader:
                myRefreshList.showOnFresh();
                //title.setVisibility(View.VISIBLE);
                break;
            case R.id.txt_showFooter:
                myRefreshList.showLoadMore();

                break;
            case R.id.txt_hideHeader:
                myRefreshList.hideOnFresh();
                //title.setVisibility(View.GONE);
                break;
            case R.id.txt_hideFooter:
                myRefreshList.hideLoadMore();
                break;
        }
    }

}