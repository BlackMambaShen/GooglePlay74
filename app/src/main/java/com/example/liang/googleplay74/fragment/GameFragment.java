package com.example.liang.googleplay74.fragment;


import android.view.View;
import android.widget.TextView;

import com.example.liang.googleplay74.View.LoadingPage;

import Utils.UIUtils;

public class GameFragment extends BaseFragment {

    @Override
    public View onCreateSuccessView() {
        TextView view=new TextView(UIUtils.getContext());
        view.setText("GameFragment");
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_SUCCESS;
    }
}
