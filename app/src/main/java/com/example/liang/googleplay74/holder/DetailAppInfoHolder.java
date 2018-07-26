package com.example.liang.googleplay74.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.liang.googleplay74.R;
import com.example.liang.googleplay74.domain.AppInfo;
import com.lidroid.xutils.BitmapUtils;

import java.text.Format;

import Utils.BitmapHelper;
import Utils.UIUtils;

/*
详情页-应用信息
 */
public class DetailAppInfoHolder extends BaseHolder<AppInfo> {
    private ImageView iv_icon;
    private TextView tv_name;
    private TextView tv_download_num;
    private TextView tv_version;
    private TextView tv_date;
    private TextView tv_size;
    private RatingBar rb_star;
    private BitmapUtils bitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_appinfo);
         iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
         tv_name = (TextView) view.findViewById(R.id.tv_name);
         tv_download_num = (TextView) view.findViewById(R.id.tv_download_num);
         tv_version = (TextView) view.findViewById(R.id.tv_version);
         tv_date = (TextView) view.findViewById(R.id.tv_date);
         tv_size = (TextView) view.findViewById(R.id.tv_size);
         rb_star = (RatingBar) view.findViewById(R.id.rb_star);
         bitmapUtils= BitmapHelper.getBitmapUtils();

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        bitmapUtils.display(iv_icon,UIUtils.getIpConfig()+data.iconUrl);
        tv_name.setText(data.name);
        tv_download_num.setText("下载量："+data.downloadNum);
        tv_version.setText("版本号："+data.version);
        tv_date.setText(data.date);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
        rb_star.setRating(data.stars);
    }
}
