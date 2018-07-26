package com.example.liang.googleplay74.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.liang.googleplay74.holder.BaseHolder;
import com.example.liang.googleplay74.holder.MoreHolder;

import java.util.ArrayList;

import Utils.UIUtils;

/*
对适配器的封装
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private ArrayList<T> data;

    private static final int TYPE_NORMAL=1;
    private static final int TYPE_MORE=0;

    public MyBaseAdapter(ArrayList<T> data){
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size()+1;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回布局类个数
    @Override
    public int getViewTypeCount() {
        return 2;//返回两种类型,普通布局 加载更多布局
    }

    //返回当前位置展示哪种布局类型
    @Override
    public int getItemViewType(int position) {
        if (position==getCount()-1){//最后一个
            return TYPE_MORE;
        }else {
            return getInnerType(position);
        }
    }

    //子类可以重写此方法来更改返回的布局类型
    public int getInnerType(int position){
        return TYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView==null){
            if (getItemViewType(position)==TYPE_MORE){
                //判断是否是加载更多
                holder=new MoreHolder(hasMore());
            }else {
                holder=getHolder(position);//子类返回布局对象
            }
        }else {
            holder=(BaseHolder)convertView.getTag();
        }
        if (getItemViewType(position)!=TYPE_MORE){
            holder.setData(getItem(position));
        }else {
            //加载更多布局
            //一旦加载更多布局展示出来，就开始加载更多
            //只有在有更多数据的状态下才加载更多
            MoreHolder moreHolder= (MoreHolder) holder;
            if (moreHolder.getData()==MoreHolder.STATE_LOAD_MORE){
                loadMore(moreHolder);
            }
        }
        return holder.getmRootView();
    }

    //子类重写此方法来决定是否可以加载更多
    public boolean hasMore(){
        return true;//默认都是有更多数据的
    }
    public abstract BaseHolder<T> getHolder(int position);

    private boolean isloadMore=false;//标记是否正在加载更多

    //加载更多数据
    public void loadMore(final MoreHolder holder){
        if (!isloadMore){
            isloadMore=true;
            new Thread(){
                @Override
                public void run() {
                    final ArrayList<T> moredata = onLoadMore();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moredata!=null){
                                //分页 每一页有20条数据，如果返回的数据小于20条，就认为到了最后一页了
                                if (moredata.size()<20){
                                    holder.setData(MoreHolder.STATE_LOAD_NONE);
                                    Toast.makeText(UIUtils.getContext(),"没有更多数据了", Toast.LENGTH_SHORT).show();
                                }else {
                                    //还有更多数据
                                    holder.setData(MoreHolder.STATE_LOAD_MORE);
                                }
                                //将更多数据追加到当前集合中
                                data.addAll(moredata);
                                //刷新界面
                                MyBaseAdapter.this.notifyDataSetChanged();
                            }else {
                                //加载更多失败
                                holder.setData(MoreHolder.STATE_LOAD_ERROR);
                            }
                            isloadMore=false;
                        }
                    });
                }
            }.start();
        }
    }

    //加载更多数据，必须由子类实现
    public abstract ArrayList<T> onLoadMore();

    public int getListSize(){
        return data.size();
    }
}
