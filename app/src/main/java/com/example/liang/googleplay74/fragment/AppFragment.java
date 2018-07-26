package com.example.liang.googleplay74.fragment;


import android.view.View;

import com.example.liang.googleplay74.View.LoadingPage;
import com.example.liang.googleplay74.View.MyListView;
import com.example.liang.googleplay74.adapter.MyBaseAdapter;
import com.example.liang.googleplay74.domain.AppInfo;
import com.example.liang.googleplay74.holder.AppHolder;
import com.example.liang.googleplay74.holder.BaseHolder;

import java.util.ArrayList;

import Utils.UIUtils;
import http.protocol.AppProtocol;

public class AppFragment extends BaseFragment {

    private ArrayList<AppInfo> data;
    //只有成功才走此方法
    @Override
    public View onCreateSuccessView() {
        MyListView view=new MyListView(UIUtils.getContext());
        view.setAdapter(new AppAdapter(data));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        AppProtocol protocol=new AppProtocol();
        data = protocol.getData(0);
        return check(data);
    }

    class AppAdapter extends MyBaseAdapter<AppInfo>{

        public AppAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int position) {
            return new AppHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            AppProtocol protocol=new AppProtocol();
            ArrayList<AppInfo> moredata = protocol.getData(getListSize());
            return moredata;
        }
    }
}
