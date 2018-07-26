package com.example.liang.googleplay74.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liang.googleplay74.R;
import com.example.liang.googleplay74.domain.CategoryInfo;
import com.lidroid.xutils.BitmapUtils;

import Utils.BitmapHelper;
import Utils.UIUtils;

public class CategoryHolder extends BaseHolder<CategoryInfo> implements View.OnClickListener{
    private TextView tvName1,tvName2,tvName3;
    private ImageView iv_icon1,iv_icon2,iv_icon3;
    private LinearLayout ll_grid1,ll_grid2,ll_grid3;
    private BitmapUtils bitmapUtils;


    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_category);
         tvName1=(TextView) view.findViewById(R.id.tv_name1);
        tvName2=(TextView) view.findViewById(R.id.tv_name2);
        tvName3=(TextView) view.findViewById(R.id.tv_name3);
         iv_icon1 = (ImageView)view.findViewById(R.id.iv_icon1);
        iv_icon2 = (ImageView)view.findViewById(R.id.iv_icon2);
        iv_icon3 = (ImageView)view.findViewById(R.id.iv_icon3);
        ll_grid1 = (LinearLayout)view.findViewById(R.id.ll_grid1);
        ll_grid2 = (LinearLayout)view.findViewById(R.id.ll_grid2);
        ll_grid3 = (LinearLayout)view.findViewById(R.id.ll_grid3);
        ll_grid1.setOnClickListener(this);
        ll_grid2.setOnClickListener(this);
        ll_grid3.setOnClickListener(this);
         bitmapUtils= BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        tvName1.setText(data.name1);
        tvName2.setText(data.name2);
        tvName3.setText(data.name3);
        bitmapUtils.display(iv_icon1,UIUtils.getIpConfig()+data.url1);
        bitmapUtils.display(iv_icon2,UIUtils.getIpConfig()+data.url2);
        bitmapUtils.display(iv_icon3,UIUtils.getIpConfig()+data.url3);
    }

    @Override
    public void onClick(View v) {
        CategoryInfo info = getData();
        switch (v.getId()){
            case R.id.ll_grid1:
                Toast.makeText(UIUtils.getContext(), info.name1, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid2:
                Toast.makeText(UIUtils.getContext(), info.name2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid3:
                Toast.makeText(UIUtils.getContext(), info.name3, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
