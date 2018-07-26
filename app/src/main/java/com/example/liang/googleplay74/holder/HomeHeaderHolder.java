package com.example.liang.googleplay74.holder;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.liang.googleplay74.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import Utils.BitmapHelper;
import Utils.UIUtils;

/*
首页轮播条holder
 */
public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {
    private ViewPager viewPager;
    private ArrayList<String> data;
    private LinearLayout llcontainer;
    private int mPreviousPos;//上个圆点位置
    @Override
    public View initView() {
        RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
        //初始化布局参数，跟布局上层控件是lisview，所以要用listview定义的LayoutParams
        AbsListView.LayoutParams params=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,UIUtils.dip2px(180));
        rlRoot.setLayoutParams(params);
        //ViewPager
         viewPager=new ViewPager(UIUtils.getContext());
         RelativeLayout.LayoutParams vpParams=new RelativeLayout.LayoutParams(
                 RelativeLayout.LayoutParams.MATCH_PARENT,
                 RelativeLayout.LayoutParams.MATCH_PARENT);

         rlRoot.addView(viewPager,vpParams);//把viewpager添加给相对布局
        //初始化指示器
         llcontainer=new LinearLayout(UIUtils.getContext());
        llcontainer.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams llParams=new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        int padding=UIUtils.dip2px(10);
        llcontainer.setPadding(padding,padding,padding,padding);
        //添加规则，设定展示位置
            llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        //添加布局
        rlRoot.addView(llcontainer,llParams);
        return rlRoot;
    }

    @Override
    public void refreshView(final ArrayList<String> data) {
        this.data=data;
        //填充viewpager的数据
        viewPager.setAdapter(new HomeHeaderAdapter());
        viewPager.setCurrentItem(data.size()*10000);//放中间 可以前后滑
        //初始化指示器
        for (int i = 0; i <data.size() ; i++) {
            ImageView point=new ImageView(UIUtils.getContext());
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i==0){//第一个默认选中
                point.setImageResource(R.drawable.indicator_selected);
            }else {
                point.setImageResource(R.drawable.indicator_normal);
                params.leftMargin=UIUtils.dip2px(6);//左边距
                point.setLayoutParams(params);
            }
            llcontainer.addView(point);
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position=position%data.size();
                ImageView point = (ImageView) llcontainer.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);
                //上个点变成不选中
                ImageView prePoint = (ImageView) llcontainer.getChildAt(mPreviousPos);
                prePoint.setImageResource(R.drawable.indicator_normal);
                mPreviousPos=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //自动滚动
        HomeHeaderTask task=new HomeHeaderTask();
        task.start();
    }

    class HomeHeaderTask implements Runnable{

        public void start(){
            //把以前的消息先移除，避免消息重复
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this,3000);
        }
        @Override
        public void run() {
            int currentItem = viewPager.getCurrentItem();
            currentItem++;
            viewPager.setCurrentItem(currentItem);
            //继续发延时3秒的消息，实现内循环
            UIUtils.getHandler().postDelayed(this,3000);
        }
    }

    class HomeHeaderAdapter extends PagerAdapter{

        private final BitmapUtils bitmapUtils;

        public HomeHeaderAdapter(){
             bitmapUtils= BitmapHelper.getBitmapUtils();
        }

        @Override
        public int getCount() {
//            return data.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            position=position%data.size();//取余，避免循环滑 位置太大
            String url = data.get(position);
            ImageView view=new ImageView(UIUtils.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            bitmapUtils.display(view,UIUtils.getIpConfig()+url);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
