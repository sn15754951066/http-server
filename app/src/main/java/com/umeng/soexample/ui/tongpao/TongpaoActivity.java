package com.umeng.soexample.ui.tongpao;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.umeng.soexample.R;
import com.umeng.soexample.ui.tongpao.home.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TongpaoActivity extends AppCompatActivity {

    @BindView(R.id.navi_tp_view)
    BottomNavigationView naviTpView;




    String[] tabs = {"推荐","广场","视频","摄影","知识文章"};
    List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongpao);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        /*fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());

        //tablayout横向滚动
        tabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );
        //初始化ViewPager
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);*/

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment,R.id.discoverFragment,R.id.shopFragment,R.id.mineFragment).build();
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment); //获得控制器
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(naviTpView,navController);
        naviTpView.setOnNavigationItemSelectedListener(listener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.homeFragment:
                    menuItem.setIcon(R.mipmap.icon_home_selected);
                    break;
                case R.id.menu_navi_discover:
                    menuItem.setIcon(R.mipmap.tabbar_find_selected);
                    break;
                case R.id.menu_navi_shop:
                    menuItem.setIcon(R.mipmap.tabbar_activity_selected);
                    break;
                case R.id.menu_navi_mine:
                    menuItem.setIcon(R.mipmap.tabbar_me_selected);
                    break;
            }
            return false;
        }
    };

    /**
     * viewpager的适配器
     */
    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get( position );
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        //写出getPagertitle方法
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
