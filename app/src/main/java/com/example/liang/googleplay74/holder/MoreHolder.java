package com.example.liang.googleplay74.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liang.googleplay74.R;

import Utils.UIUtils;

public class MoreHolder extends BaseHolder<Integer> {
    /*
    加载更多的几种状态
    1.可以加载更多
    2.加载更多失败
    3.没有更多数据
     */

    public static final int STATE_LOAD_MORE=1;
    public static final int STATE_LOAD_ERROR=2;
    public static final int STATE_LOAD_NONE=3;
    private LinearLayout ll_load_more;
    private TextView tv_load_error;

    public MoreHolder(boolean hasMore) {
        //如果有更多数据，状态为more，将此状态传给父类的data,父类同时刷新界面
        if (hasMore){
            setData(STATE_LOAD_MORE);
        }else {
            setData(STATE_LOAD_NONE);
        }
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
         ll_load_more = (LinearLayout)view.findViewById(R.id.ll_load_more);
        tv_load_error = (TextView)view.findViewById(R.id.tv_load_error);
        return view;
    }

    @Override
    public void refreshView(Integer data) {
        switch (data){
            case STATE_LOAD_MORE:
                //显示加载更多
                ll_load_more.setVisibility(View.VISIBLE);
                tv_load_error.setVisibility(View.GONE);
                break;
            case STATE_LOAD_NONE:
                //隐藏加载更多
                ll_load_more.setVisibility(View.GONE);
                tv_load_error.setVisibility(View.GONE);
                break;

            case STATE_LOAD_ERROR:
                //显示加载失败的布局
                ll_load_more.setVisibility(View.GONE);
                tv_load_error.setVisibility(View.VISIBLE);
                break;
        }
    }
}
