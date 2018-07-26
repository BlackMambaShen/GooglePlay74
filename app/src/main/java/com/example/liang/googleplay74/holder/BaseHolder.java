package com.example.liang.googleplay74.holder;

import android.view.View;

public abstract class BaseHolder<T> {

    private final View mRootView;//一个item的根布局
    private T data;

    public BaseHolder(){
         mRootView = initView();
        //3.打一个标记tag
         mRootView.setTag(this);
    }
    //1.加载布局文件
    //2.初始化控件findViewById
    public abstract View initView();

    //返回item布局对象
    public View getmRootView(){
        return mRootView;
    }

    //设置数据
    public void setData(T data){
        this.data=data;
        refreshView(data);
    }

    //获取数据
    public T getData(){
        return data;
    }

    //4.根据数据刷新界面
    public abstract void refreshView(T data);
}
