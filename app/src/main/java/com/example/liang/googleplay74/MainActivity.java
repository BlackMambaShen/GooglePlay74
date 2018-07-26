package com.example.liang.googleplay74;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.liang.googleplay74.View.PagerTab;
import com.example.liang.googleplay74.activity.BaseActivity;
import com.example.liang.googleplay74.fragment.BaseFragment;
import com.example.liang.googleplay74.fragment.FragmentFactory;

import Utils.UIUtils;

import static com.example.liang.googleplay74.R.string.drawer_close;

public class MainActivity extends BaseActivity {

    private PagerTab pager_tab;
    private ViewPager viewpager;
    private MyAdapter myAdapter;
    private android.support.v4.app.ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         pager_tab = (PagerTab)findViewById(R.id.pager_tab);
         viewpager = (ViewPager)findViewById(R.id.viewpager);
          myAdapter=new MyAdapter(getSupportFragmentManager());
          viewpager.setAdapter(myAdapter);
          pager_tab.setViewPager(viewpager);//将指针和viewpager绑定在一起

        //设置监听
         pager_tab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }

             @Override
             public void onPageSelected(int position) {
                 BaseFragment fragment = FragmentFactory.createFragment(position);
                 //开始加载数据
                    fragment.loadData();
             }

             @Override
             public void onPageScrollStateChanged(int state) {

             }
         });

         initActionbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //切换抽屉
            toggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends FragmentPagerAdapter{

        private final String[] mTabNames;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabNames = UIUtils.getStringArray(R.array.tab_names);//加载页签标题的数组
        }

        //返回当前页面的fragment对象
        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return mTabNames.length;
        }


        //返回页签的标题
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }
    }
    //初始化actionbar
    private void initActionbar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);//home可以被点击
        actionBar.setDisplayHomeAsUpEnabled(true);//显示左上角返回键
        DrawerLayout drawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        //抽屉的开关
        toggle=new android.support.v4.app.ActionBarDrawerToggle(this,drawerLayout,R.drawable.ic_drawer_am,
                R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();//同步状态，layout和开关关联在一起
    }
}
