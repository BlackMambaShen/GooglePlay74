package com.example.liang.googleplay74.fragment;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.liang.googleplay74.View.FlowLayout;
import com.example.liang.googleplay74.View.LoadingPage;

import java.util.ArrayList;
import java.util.Random;

import Utils.DrawableUtils;
import Utils.UIUtils;
import http.protocol.HotProtocol;

public class HotFragment extends BaseFragment {

    private ArrayList<String> data;

    @Override
    public View onCreateSuccessView() {
        //支持上下滑动
        ScrollView scrollView=new ScrollView(UIUtils.getContext());
        FlowLayout flow=new FlowLayout(UIUtils.getContext());
        int padding=UIUtils.dip2px(10);
        flow.setPadding(padding,padding,padding,padding);//设置内边距
        flow.setHorizontalSpacing(UIUtils.dip2px(6));//水平间距
        flow.setVerticalSpacing(UIUtils.dip2px(8));//竖直间距
        for (int i = 0; i <data.size() ; i++) {
            TextView view=new TextView(UIUtils.getContext());
            String keyword = data.get(i);
            view.setText(keyword);
            view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            view.setPadding(padding,padding,padding,padding);
            view.setGravity(Gravity.CENTER);
            Random random=new Random();
            int r=30+random.nextInt(200);
            int g=30+random.nextInt(200);
            int b=30+random.nextInt(200);
            int color=0xffcecece;
            GradientDrawable bgNormal = DrawableUtils.getGradientDrawable(Color.rgb(r, g, b), UIUtils.dip2px(6));
            GradientDrawable bgPress = DrawableUtils.getGradientDrawable(color, UIUtils.dip2px(6));
            StateListDrawable selector = DrawableUtils.getSelector(bgNormal, bgPress);
            view.setBackgroundDrawable(selector);
            flow.addView(view);
            //设置点击事件，状态选择器才起作用
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        scrollView.addView(flow);
        return scrollView;
    }

    //运行在子线程，可以直接执行耗时操作
    @Override
    public LoadingPage.ResultState onLoad() {
        HotProtocol protocol=new HotProtocol();
        data = protocol.getData(0);
        return check(data);
    }
}
