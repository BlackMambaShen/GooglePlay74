package com.example.liang.googleplay74.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.liang.googleplay74.R;
import com.example.liang.googleplay74.domain.AppInfo;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import Utils.BitmapHelper;
import Utils.UIUtils;

/*
首页详情页-截图
 */
public class DetailPicsHolder extends BaseHolder<AppInfo> {

    private ImageView[]ivPics;
    private BitmapUtils bitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_picinfo);
        ivPics=new ImageView[5];
        ivPics[0]=view.findViewById(R.id.iv_pic1);
        ivPics[1]=view.findViewById(R.id.iv_pic2);
        ivPics[2]=view.findViewById(R.id.iv_pic3);
        ivPics[3]=view.findViewById(R.id.iv_pic4);
        ivPics[4]=view.findViewById(R.id.iv_pic5);
         bitmapUtils= BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        final ArrayList<String> screen = data.screen;
        for (int i = 0; i <5 ; i++) {
            if (i<screen.size()){
                bitmapUtils.display(ivPics[i],UIUtils.getIpConfig()+screen.get(i));
                //看大图
//                ivPics[i].setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //跳转activity,activity展示vp
//                        //将集合通过intent
//                        Intent intent=new Intent();
//                        intent.putExtra("list",screen);
//                    }
//                });
            }else {
                ivPics[i].setVisibility(View.GONE);
            }
        }
    }
}
