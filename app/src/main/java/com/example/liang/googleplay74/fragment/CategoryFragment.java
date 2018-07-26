package com.example.liang.googleplay74.fragment;


import android.view.View;

import com.example.liang.googleplay74.View.LoadingPage;
import com.example.liang.googleplay74.View.MyListView;
import com.example.liang.googleplay74.adapter.MyBaseAdapter;
import com.example.liang.googleplay74.domain.CategoryInfo;
import com.example.liang.googleplay74.holder.BaseHolder;
import com.example.liang.googleplay74.holder.CategoryHolder;
import com.example.liang.googleplay74.holder.TitleHolder;

import java.util.ArrayList;

import Utils.UIUtils;
import http.protocol.CategoryProtocol;

public class CategoryFragment extends BaseFragment {

    private ArrayList<CategoryInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView view=new MyListView(UIUtils.getContext());
        view.setAdapter(new CategoryAdapter(data));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        CategoryProtocol protocol=new CategoryProtocol();
        data = protocol.getData(0);
        return check(data);
    }

    class CategoryAdapter extends MyBaseAdapter<CategoryInfo>{

        public CategoryAdapter(ArrayList<CategoryInfo> data) {
            super(data);
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;//在原来基础上，增加一种标题类型
        }

        @Override
        public int getInnerType(int position) {
            //判断是标题类型还是普通分类类型
            CategoryInfo info = data.get(position);
            if (info.isTitle){
                //返回标题类型
                return super.getInnerType(position)+1;//原来类型基础上加1,保证：normal的类型为1
            }else {
                //返回普通类型
                return super.getInnerType(position);
            }
        }

        @Override
        public BaseHolder<CategoryInfo> getHolder(int position) {
            //判断标题类型还是普通分类类型，来返回不同的holder
            CategoryInfo info = data.get(position);
            if (info.isTitle){
                return new TitleHolder();
            }else {
                return new CategoryHolder();
            }
        }

        @Override
        public ArrayList<CategoryInfo> onLoadMore() {
            return null;
        }

        //没有更多数据
        @Override
        public boolean hasMore() {
            return false;//没有更多数据，需要隐藏加载更多布局
        }
    }
}
