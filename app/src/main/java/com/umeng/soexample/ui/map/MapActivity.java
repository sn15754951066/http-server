package com.umeng.soexample.ui.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PlaneInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiAddrInfo;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.mapapi.utils.poi.DispathcPoiData;
import com.umeng.soexample.R;
import com.umeng.soexample.base.BaseAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mapapi.clusterutil.clustering.ClusterItem;
import mapapi.clusterutil.clustering.ClusterManager;
import mapapi.overlayutil.WalkingRouteOverlay;

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

    //路径规划
    @BindView(R.id.input_start)
    EditText inputStart;
    @BindView(R.id.input_end)
    EditText inputEnd;
    @BindView(R.id.btn_routePlan)
    Button btnRoutePlan;
    @BindView(R.id.recy_nodes)
    RecyclerView recyNodes;
    @BindView(R.id.layout_edit)
    LinearLayout layoutEdit;

    //百度地图的数据操作
    BaiduMap baiduMap;
    //百度地图定位的类
    LocationClient locationClient;

    int r = 300;

    /********************检索********************/
    SearchItemAdapter searchItemAdapter;
    List<PoiInfo> poiList;

    /********************检索***********************/



    /*******************路径规划********************/
    RoutePlanSearch routePlanSearch;

    /*******************路径规划********************/

    /************行政区域*************/
    DistrictSearch districtSearch;

    //点聚合
    ClusterManager clusterManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        initView();
        initMap();
        initLocation();
        //初始化检索
        initPoi();
        //初始化路径规划
        initRoutePlan();
        //行政区域
        initDistrict();

        //点聚合
        initCluster();

       /* ConstraintLayout relativeLayout = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.activity_news,null);
        layoutEdit.addView(relativeLayout);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) relativeLayout.getLayoutParams();
        if(params == null){
            params = (ConstraintLayout.LayoutParams) new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        */

    }

    private void initView() {
        btnSearch.setOnClickListener(this);
        btnRoutePlan.setOnClickListener(this);
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
            case R.id.btn_routePlan:
                searchRoute();
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
                //点聚合
                List<MyItem> list = new ArrayList<>();
                for(PoiInfo item:poiResult.getAllPoi()){
                    // 添加Marker点
                    LatLng ll = new LatLng(item.getLocation().latitude, item.getLocation().longitude);
                    MyItem myItem = new MyItem(ll);
                    list.add(myItem);
                }
                updateCluster(list);

               /* poiList.addAll(poiResult.getAllPoi());
                searchItemAdapter.notifyDataSetChanged();*/
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


    /*******************************路径规划*************************/
    private PlanNode startNode,endNode; //开始和结束的坐标点
    SuggestionSearch suggestionSearch; //地点检索的类
    SuggestAdapter suggestAdapter; //路径规划搜索出来的列表
    List<SuggestionResult.SuggestionInfo> suggestList; //地点检索的结果
    boolean isStart = true; //当前处理的是否是起点
    LatLng startLatLng; //起点的经纬度


    //初始化路径规划
    private void initRoutePlan(){

        suggestionSearch = SuggestionSearch.newInstance();
        suggestList = new ArrayList<>();
        suggestAdapter = new SuggestAdapter(this,suggestList);
        recyNodes.setLayoutManager(new LinearLayoutManager(this));
        recyNodes.setAdapter(suggestAdapter);
        //设置监听地点检索
        suggestionSearch.setOnGetSuggestionResultListener(suggestionResultListener);

        inputStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    isStart = true;
                    recyNodes.setVisibility(View.VISIBLE);
                }
            }
        });
        //监听起点输入框的变化
        inputStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //起点输入改变以后获取对应的起点数据
                SuggestionSearchOption option = new SuggestionSearchOption();
                option.city("北京");
                option.citylimit(true);
                option.keyword(s.toString());
                suggestionSearch.requestSuggestion(option);
            }
        });
        //监听终点输入框的光标
        inputEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    isStart = false;
                    recyNodes.setVisibility(View.VISIBLE);
                }
            }
        });
        //监听终点输入框的输入
        inputEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //获取终点框对应的数据
                SuggestionSearchOption option = new SuggestionSearchOption();
                option.city("北京");
                option.citylimit(true);
                option.keyword(s.toString());
                suggestionSearch.requestSuggestion(option);
            }
        });


        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(routePlanResultListener);

        suggestAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                SuggestionResult.SuggestionInfo suggestionInfo = suggestList.get(pos);
                if(isStart){
                    inputStart.setText(suggestionInfo.getKey());
                    startLatLng = suggestionInfo.getPt();
                }else{
                    inputEnd.setText(suggestionInfo.getKey());
                }
                recyNodes.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 地点检索监听
     */
    OnGetSuggestionResultListener suggestionResultListener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            //地点检索结果
            suggestList.clear();
            suggestList.addAll(suggestionResult.getAllSuggestions());
            suggestAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 路径搜索的监听
     */
    OnGetRoutePlanResultListener routePlanResultListener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            Log.i(TAG,"onGetWalkingRouteResult");

            //创建一个路径规划的类
            WalkingRouteOverlay walkingRouteOverlay = new WalkingRouteOverlay(baiduMap);
            //判断当前查找出来的路线
            if(walkingRouteResult.getRouteLines() != null && walkingRouteResult.getRouteLines().size() > 0){
                WalkingRouteLine walkingRouteLine = walkingRouteResult.getRouteLines().get(0);
                walkingRouteOverlay.setData(walkingRouteLine);
                walkingRouteOverlay.addToMap();
                //当前的定位移动到开始点并放大地图
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLngZoom(startLatLng,16);
                baiduMap.setMapStatus(status);
            }else{
                Toast.makeText(MapActivity.this,"未搜索到相关路径",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            Log.i(TAG,"onGetTransitRouteResult");
        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
            Log.i(TAG,"onGetMassTransitRouteResult");
        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            Log.i(TAG,"onGetDrivingRouteResult");
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
            Log.i(TAG,"onGetIndoorRouteResult");
        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            Log.i(TAG,"onGetBikingRouteResult");
        }
    };

    private void searchRoute(){
        String startName,endName;
        startName = inputStart.getText().toString();
        endName = inputEnd.getText().toString();
        if(TextUtils.isEmpty(startName) || TextUtils.isEmpty(endName)){
            Toast.makeText(this, "请输入正确起点和终点", Toast.LENGTH_SHORT).show();
        }else{
            startNode = PlanNode.withCityNameAndPlaceName("北京",startName);
            endNode = PlanNode.withCityNameAndPlaceName("北京",endName);
            WalkingRoutePlanOption option = new WalkingRoutePlanOption();
            option.from(startNode);
            option.to(endNode);
            //搜索路径
            routePlanSearch.walkingSearch(option);
        }
    }


    /******************行政区域********************/
    private void initDistrict(){
        districtSearch = DistrictSearch.newInstance();
        districtSearch.setOnDistrictSearchListener(districSearchResultListener);
        districtSearch.searchDistrict(new DistrictSearchOption()
        .cityName("北京")
        .districtName("海淀区"));
    }

    //行政区域的监听
    OnGetDistricSearchResultListener districSearchResultListener = new OnGetDistricSearchResultListener() {
        @Override
        public void onGetDistrictResult(DistrictResult districtResult) {
            if(null != districtResult){
                //数据
                baiduMap.clear();
                //获取边界坐标点，并展示
                if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    List<List<LatLng>> polyLines = districtResult.getPolylines();
                    if (polyLines == null) {
                        return;
                    }
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (List<LatLng> polyline : polyLines) {
                        OverlayOptions ooPolyline11 = new PolylineOptions().width(10)
                                .points(polyline).dottedLine(true).color(Color.BLUE);
                        baiduMap.addOverlay(ooPolyline11);
                        OverlayOptions ooPolygon = new PolygonOptions().points(polyline)
                                .stroke(new Stroke(5, 0xAA00FF88)).fillColor(0xAAFFFF00);
                        baiduMap.addOverlay(ooPolygon);
                        for (LatLng latLng : polyline) {
                            builder.include(latLng);
                        }
                    }
                    baiduMap.setMapStatus(MapStatusUpdateFactory
                            .newLatLngBounds(builder.build()));

                }
            }
        }
    };

    /**************点聚合******************/
    private void initCluster(){
        //初始化点聚合管理类
        clusterManager = new ClusterManager<MyItem>(this, baiduMap);
    }

    //ClusterItem接口的实现类
    public class MyItem implements ClusterItem {
        LatLng mPosition;
        public MyItem(LatLng position) {
            mPosition = position;
        }
        @Override
        public LatLng getPosition() {
            return mPosition;
        }
        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            return BitmapDescriptorFactory
                    .fromResource(R.mipmap.icon_mark);
        }
    }

    private void updateCluster(List<MyItem> clusters){
        clusterManager.addItems(clusters);
    }

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
                .fillColor(0x50ff0000)
                .stroke(new Stroke(2,0xff000000)); //设置边框的宽度和颜色
        baiduMap.clear();
        //在地图上添加显示圆
        Overlay circle = baiduMap.addOverlay(circleOptions);

    }

    private void addMark(double lat,double gt){
        //定义Maker坐标点
        LatLng point = new LatLng(lat, gt);
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