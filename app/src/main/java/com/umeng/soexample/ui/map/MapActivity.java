package com.umeng.soexample.ui.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MapActivity.class.getSimpleName();


    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //百度地图的数据操作
    BaiduMap baiduMap;
    //百度地图定位的类
    LocationClient locationClient;

    int r = 300;

    /********************检索********************/
    SearchItemAdapter searchItemAdapter;
    List<PoiInfo> poiList;

    /********************检索***********************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        initView();
        initMap();
        initLocation();

        initPoi();
    }

    private void initView() {
        btnSearch.setOnClickListener(this);
    }

    private void initMap() {
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        //设置为普通类型的地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    /**
     * 初始化定位
     */
    private void initLocation(){
        //定位初始化
        locationClient = new LocationClient(this);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

        //设置locationClientOption
        locationClient.setLocOption(option);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        locationClient.start();
    }


    /**
     * 地图定位的监听
     */
    private class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //mapView 销毁后不在处理新接收的位置
            if (bdLocation == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            //drawCircle(bdLocation.getLatitude(),bdLocation.getLongitude());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                search();
                break;
        }
    }

    /************************检索*********************/
    PoiSearch poiSearch;

    private void initPoi(){
        poiList = new ArrayList<>();
        searchItemAdapter = new SearchItemAdapter(this,poiList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(searchItemAdapter);

        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(poiSearchResultListener);

        searchItemAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                //点击条目进行定位
                PoiInfo poiInfo = poiList.get(pos);
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(poiInfo.location);
                baiduMap.setMapStatus(status);
                drawCircle(poiInfo.location.latitude,poiInfo.location.longitude);
                addMark(poiInfo.location.latitude,poiInfo.location.longitude);
            }
        });
    }

    /**
     * 搜索
     */
    private void search(){
        String word = input.getText().toString();
        if(!TextUtils.isEmpty(word)){
            PoiCitySearchOption option = new PoiCitySearchOption();
            option.city("北京");
            option.keyword(word);
            poiSearch.searchInCity(option);
        }else{

        }
    }

    /**
     * 搜索的监听
     */
    OnGetPoiSearchResultListener poiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            Log.i(TAG,"onGetPoiResult");
            poiList.clear();
            if(poiResult.getAllPoi() != null && poiResult.getAllPoi().size() > 0){
                poiList.addAll(poiResult.getAllPoi());
                searchItemAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            Log.i(TAG,"onGetPoiDetailResult");
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
            Log.i(TAG,"onGetPoiDetailResult");
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            Log.i(TAG,"onGetPoiIndoorResult");
        }
    };

    /**
     * 以当前的经纬度为圆心绘制一个圆
     * @param lat
     * @param gt
     */
    private void drawCircle(double lat,double gt){
        //设置圆心位置
        LatLng center = new LatLng(lat,gt);
        //设置圆对象
        CircleOptions circleOptions = new CircleOptions().center(center)
                .radius(2000)
                .fillColor(0xff0000)
                .stroke(new Stroke(10,0x000000)); //设置边框的宽度和颜色
        //在地图上添加显示圆
        Overlay circle = baiduMap.addOverlay(circleOptions);
    }

    private void addMark(double lat,double gt){
        //定义Maker坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_mark);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
    }





















    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
}