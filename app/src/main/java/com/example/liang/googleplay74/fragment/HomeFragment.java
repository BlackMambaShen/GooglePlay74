package com.example.liang.googleplay74.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liang.googleplay74.R;
import com.example.liang.googleplay74.View.LoadingPage;
import com.example.liang.googleplay74.View.MyListView;
import com.example.liang.googleplay74.activity.HomeDetalActivity;
import com.example.liang.googleplay74.adapter.MyBaseAdapter;
import com.example.liang.googleplay74.domain.AppInfo;
import com.example.liang.googleplay74.holder.BaseHolder;
import com.example.liang.googleplay74.holder.HomeHeaderHolder;
import com.example.liang.googleplay74.holder.HomeHolder;

import java.util.ArrayList;

import Utils.UIUtils;
import http.protocol.HomeProtocol;

public class HomeFragment extends BaseFragment {

    private ArrayList<AppInfo> data;
    private ArrayList<String> pictureList;

    @Override
    public View onCreateSuccessView() {
        MyListView view=new MyListView(UIUtils.getContext());
        //给lv增加头布局展示轮播条
        HomeHeaderHolder header=new HomeHeaderHolder();
        view.addHeaderView(header.getmRootView());
        if (pictureList!=null){
            //设置轮播条
            header.setData(pictureList);
        }
        view.setAdapter(new HomeAdapter(data));

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = data.get(position-1);//-1去掉头布局
                if (appInfo!=null){
                    Intent intent=new Intent(UIUtils.getContext(), HomeDetalActivity.class);
                    intent.putExtra("packageName",appInfo.packageName);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        //请求网络
//        data=new ArrayList<String>();
//        for (int i = 0; i <20 ; i++) {
//            data.add("测试数据："+i);
//        }

        HomeProtocol protocol=new HomeProtocol();
        //拿到网络数据
        data = protocol.getData(0);
        pictureList = protocol.getPictureList();
        return check(data);//校驗數據并返回
    }


    class HomeAdapter extends MyBaseAdapter<AppInfo>{
        public HomeAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int position) {
            return new HomeHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
//            ArrayList<String>moreData=new ArrayList<String>();
//            for (int i = 0; i <20 ; i++) {
//                moreData.add("测试更多数据:"+i);
//            }
//            SystemClock.sleep(2000);
            HomeProtocol protocol=new HomeProtocol();
            //下一頁數據的位置等於當前集合大小
            ArrayList<AppInfo> moreData = protocol.getData(getListSize());
            return moreData;
        }

        @Override
        public boolean hasMore() {
            return true;
        }

        //        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView==null){
//                //1.加载布局文件
//                convertView=View.inflate(UIUtils.getContext(), R.layout.list_item_home,null);
//                holder=new ViewHolder();
//                //2.初始化控件findViewById
//                holder.tv=convertView.findViewById(R.id.tv_content);
//                //3.打一个标记tag
//                convertView.setTag(holder);
//            }else {
//                holder= (ViewHolder) convertView.getTag();
//            }
//            //4.根据数据来刷新界面
//            holder.tv.setText(data.get(position));
//            return convertView;
//        }
    }
//
//    static class ViewHolder{
//        TextView tv;
//    }
}
