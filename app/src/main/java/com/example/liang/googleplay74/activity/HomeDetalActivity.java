package com.example.liang.googleplay74.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.example.liang.googleplay74.R;
import com.example.liang.googleplay74.View.LoadingPage;
import com.example.liang.googleplay74.domain.AppInfo;
import com.example.liang.googleplay74.holder.DetailAppInfoHolder;
import com.example.liang.googleplay74.holder.DetailDesHolder;
import com.example.liang.googleplay74.holder.DetailDownloadHolder;
import com.example.liang.googleplay74.holder.DetailPicsHolder;
import com.example.liang.googleplay74.holder.DetailSafeHolder;

import Utils.UIUtils;
import http.protocol.HomeDetailProtocol;

public class HomeDetalActivity extends AppCompatActivity{
    private LoadingPage loadingPage;
    private String packageName;
    private AppInfo data;
    private HorizontalScrollView hsv_detailPics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         loadingPage=new LoadingPage(this) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetalActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetalActivity.this.onLoad();
            }
        };

        setContentView(loadingPage);//直接将一个view对象设置给activity
         packageName = getIntent().getStringExtra("packageName");
        //开始加载网络数据
        loadingPage.loadData();
    }

    public View onCreateSuccessView(){
        //初始化成功的布局
        View view = UIUtils.inflate(R.layout.page_home_detail);
        FrameLayout flDetailAppInfo = (FrameLayout) view.findViewById(R.id.fl_detail_appInfo);
        DetailAppInfoHolder appInfoHolder=new DetailAppInfoHolder();
        flDetailAppInfo.addView(appInfoHolder.getmRootView());
        appInfoHolder.setData(data);
        //初始化安全描述模块
        FrameLayout flDetailSafe = (FrameLayout) view.findViewById(R.id.fl_detail_safe);
        DetailSafeHolder safeHolder=new DetailSafeHolder();
        flDetailSafe.addView(safeHolder.getmRootView());
        safeHolder.setData(data);
        //初始化截图模块
        DetailPicsHolder picsHolder=new DetailPicsHolder();
         hsv_detailPics = (HorizontalScrollView)view.findViewById(R.id.hsv_detailPics);
         hsv_detailPics.addView(picsHolder.getmRootView());
         picsHolder.setData(data);
         //初始化描述模块
        FrameLayout flDetailDes=(FrameLayout) view.findViewById(R.id.fl_detail_des);
        DetailDesHolder desHolder=new DetailDesHolder();
        flDetailDes.addView(desHolder.getmRootView());
        desHolder.setData(data);

        //初始化下载模块
        FrameLayout flDetailDownload=(FrameLayout) view.findViewById(R.id.fl_deatil_download);
        DetailDownloadHolder downloadHolder=new DetailDownloadHolder();
        flDetailDownload.addView(downloadHolder.getmRootView());
        downloadHolder.setData(data);
        return view;
    }

    public LoadingPage.ResultState onLoad(){
        //请求网络，加载数据
        HomeDetailProtocol protocol=new HomeDetailProtocol(packageName);
         data = protocol.getData(0);
        if (data!=null){
            return LoadingPage.ResultState.STATE_SUCCESS;
        }else {
            return LoadingPage.ResultState.STATE_ERROR;
        }
    }
}
