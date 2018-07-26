package com.example.liang.googleplay74.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liang.googleplay74.View.LoadingPage;

import java.util.ArrayList;

import Utils.UIUtils;

public abstract class BaseFragment extends Fragment {
    private LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TextView view=new TextView(UIUtils.getContext());
//        view.setText(getClass().getSimpleName());
         loadingPage= new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                //注意 此处一定要调用BaseFragment的onCreateSuccessView，否则栈溢出
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };
        return loadingPage;
    }

    //加载成功的布局,必须由子类来实现
    public abstract View onCreateSuccessView();

    //加载网络数据，必须由子类来实现
    public abstract LoadingPage.ResultState onLoad();

    //加载数据
    public void loadData(){
        if (loadingPage!=null){
            loadingPage.loadData();
        }
    }

    //對網絡返回數據的合法性進行校驗
    public LoadingPage.ResultState check(Object obj){
        if(obj!=null){
            if (obj  instanceof ArrayList){//判斷是否是一個集合
                ArrayList list= (ArrayList) obj;
                if (list.isEmpty()){
                    return LoadingPage.ResultState.STATE_EMPTY;
                }else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }
}
