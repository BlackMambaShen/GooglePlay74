package com.example.liang.googleplay74.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.liang.googleplay74.R;
import com.example.liang.googleplay74.domain.AppInfo;
import com.lidroid.xutils.BitmapUtils;

import java.util.Formatter;

import Utils.BitmapHelper;
import Utils.UIUtils;

public class HomeHolder extends BaseHolder<AppInfo> {
    private TextView tvContent;
    private TextView tv_name;
    private TextView tv_size;
    private TextView tv_des;
    private ImageView iv_icon;
    private RatingBar rb_star;
    private BitmapUtils bitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_home);
        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_size = (TextView)view.findViewById(R.id.tv_size);
        tv_des = (TextView)view.findViewById(R.id.tv_des);
        iv_icon = (ImageView)view.findViewById(R.id.iv_icon);
        rb_star = (RatingBar)view.findViewById(R.id.rb_star);
        bitmapUtils= BitmapHelper.getBitmapUtils();
        return view;
    }

    public void refreshView(AppInfo data) {
        tv_name.setText(data.name);
        tv_size.setText(android.text.format.Formatter.formatFileSize(UIUtils.getContext(),data.size));
        tv_des.setText(data.des);
        rb_star.setRating(data.stars);
        bitmapUtils.display(iv_icon,UIUtils.getIpConfig()+data.iconUrl);
    }
}
