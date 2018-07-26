package com.example.liang.googleplay74.fragment;


import android.view.View;

import com.example.liang.googleplay74.View.LoadingPage;
import com.example.liang.googleplay74.View.MyListView;
import com.example.liang.googleplay74.adapter.MyBaseAdapter;
import com.example.liang.googleplay74.domain.AppInfo;
import com.example.liang.googleplay74.domain.SubjectInfo;
import com.example.liang.googleplay74.holder.BaseHolder;
import com.example.liang.googleplay74.holder.SubjectHolder;

import java.util.ArrayList;

import Utils.UIUtils;
import http.protocol.AppProtocol;
import http.protocol.SubjectProtocol;

public class SubjectFragment extends BaseFragment {

    private ArrayList<SubjectInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView view=new MyListView(UIUtils.getContext());
        view.setAdapter(new SubjectAdapter(data));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        SubjectProtocol protocol=new SubjectProtocol();
        data = protocol.getData(0);
        return check(data);
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{

        public SubjectAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<SubjectInfo> getHolder(int position) {
            return new SubjectHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onLoadMore() {
            SubjectProtocol protocol=new SubjectProtocol();
            ArrayList<SubjectInfo> moredata = protocol.getData(getListSize());
            return moredata;
        }
    }

}
