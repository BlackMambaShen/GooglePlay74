package com.example.liang.googleplay74.fragment;


import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liang.googleplay74.View.LoadingPage;
import com.example.liang.googleplay74.View.View.fly.ShakeListener;
import com.example.liang.googleplay74.View.View.fly.StellarMap;

import java.util.ArrayList;
import java.util.Random;

import Utils.UIUtils;
import http.protocol.RecommendProtocol;

public class RecommendFragment extends BaseFragment {

    private ArrayList<String> data;

    @Override
    public View onCreateSuccessView() {
        final StellarMap stellar=new StellarMap(UIUtils.getContext());
        stellar.setAdapter(new RecommendAdapter());
        int padding = UIUtils.dip2px(10);
        stellar.setInnerPadding(padding,padding,padding,padding);
        //设置默认页面
        stellar.setGroup(0,true);
        //随机方式，9行 6列
        stellar.setRegularity(6,9);
        ShakeListener shake=new ShakeListener(UIUtils.getContext());
        shake.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                stellar.zoomIn();//跳到下一页数据
            }
        });
        return stellar;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        RecommendProtocol protocol=new RecommendProtocol();
        data = protocol.getData(0);
        return check(data);
    }

    class RecommendAdapter implements StellarMap.Adapter {

        //返回组的个数
        @Override
        public int getGroupCount() {
            return 2;
        }

        //返回某组的item个数
        @Override
        public int getCount(int group) {
            int count = data.size() / getGroupCount();
            if (group==getGroupCount()-1){
                //最后一页，将除不尽，余下来的数量追加在最后一页，保证数据完整不丢失
                count+=data.size()%getGroupCount();
            }
            return count;
        }

        //初始化布局
        @Override
        public View getView(int group, int position, View convertView) {
            position+=(group)*getCount(group-1);
            String keyword = data.get(position);
            TextView view=new TextView(UIUtils.getContext());
            view.setText(keyword);
            Random random=new Random();
            //随机大小
            int size=16+random.nextInt(10);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
            //随机颜色
            int r=30+random.nextInt(200);
            int g=30+random.nextInt(200);
            int b=30+random.nextInt(200);
            view.setTextColor(Color.rgb(r,g,b));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), "你真的很帅！", Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

        //返回下一组的id
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (isZoomIn){
                //往下滑加载上一页
                if (group>0){
                    group--;
                }else {
                    //调到最后一页
                    group=getGroupCount()-1;
                }
            }else {
                //往上滑加载下一页
                if (group<getGroupCount()-1){
                    group++;
                }else {
                    group=0;
                }
            }
            return group;
        }
    }
}
