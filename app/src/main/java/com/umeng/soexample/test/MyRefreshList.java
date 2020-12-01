package com.umeng.soexample.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.umeng.soexample.R;

/**
 * 模拟实现一个下拉刷新，上拉加载
 */
public class MyRefreshList extends LinearLayout {
    //header-view
    private View headerView;
    //footer-view
    private View footerView;
    //获取当前的列表
    private RecyclerView recyclerView;


    public MyRefreshList(Context context) {
        this(context,null);
    }

    public MyRefreshList(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRefreshList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);

    }

    public MyRefreshList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initHeader();
        initFooter();
    }

    private void initHeader(){
        if(headerView == null){
            headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_refresh_header,this,false);
        }
        if(this.getChildCount() > 0){
            if(headerView.getParent() != null){
                Log.i("TAG","headerView parent is true");
            }else{
                addView(headerView,0);
            }
        }else{
            if(headerView.getParent() != null){
                Log.i("TAG","headerView parent is true");
            }else{
                addView(headerView);
            }
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) headerView.getLayoutParams();
        if(params == null){
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,80);
        }
        params.height = 80;
        headerView.setLayoutParams(params);
        headerView.setVisibility(GONE);
    }

    private void initFooter(){
        if(footerView == null){
            footerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_refresh_footer,this,false);
        }
        addView(footerView);
        footerView.setVisibility(GONE);
    }

    /**
     * 初始化当前的自定义刷新的布局
     */
    public void init(){
        for(int i=0; i<this.getChildCount(); i++){
            View item = this.getChildAt(i);
            if(item instanceof RecyclerView){
                recyclerView = (RecyclerView) item;
                break;
            }
        }
        //检测列表和footer位置
        setChildrenDrawingOrderEnabled(true);
        if(footerView.getParent() != null){
            this.removeView(footerView);
            this.addView(footerView);
        }
    }


    public void showOnFresh(){
        if(headerView != null) headerView.setVisibility(VISIBLE);
    }

    public void hideOnFresh(){
        if(headerView != null) headerView.setVisibility(GONE);
    }

    public void showLoadMore(){
        if(footerView != null) footerView.setVisibility(VISIBLE);
    }

    public void hideLoadMore(){
        if(footerView != null) footerView.setVisibility(GONE);
    }


    public interface onFreshListener{
        void success();
        void fail();
    }

    public interface onLoadMoreListener{
        void successd();
        void fail();
    }





}
