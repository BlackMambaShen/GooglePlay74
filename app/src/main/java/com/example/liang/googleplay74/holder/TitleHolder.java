package com.example.liang.googleplay74.holder;

import android.view.View;
import android.widget.TextView;

import com.example.liang.googleplay74.R;
import com.example.liang.googleplay74.domain.CategoryInfo;

import Utils.UIUtils;

/*
分类模块 标题布局
 */
public class TitleHolder extends BaseHolder<CategoryInfo> {
    private TextView tv_title;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_title);
         tv_title = (TextView)view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        tv_title.setText(data.title);
    }
}
