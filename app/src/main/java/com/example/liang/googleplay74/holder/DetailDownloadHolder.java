package com.example.liang.googleplay74.holder;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.liang.googleplay74.R;
import com.example.liang.googleplay74.View.ProgressHorizontal;
import com.example.liang.googleplay74.domain.AppInfo;
import com.example.liang.googleplay74.domain.DownloadInfo;
import com.example.liang.googleplay74.manager.DownLoadManager;
import Utils.UIUtils;

/*
详情页 下载模块
 */
public class DetailDownloadHolder extends BaseHolder<AppInfo> implements View.OnClickListener{

    private DownLoadManager mDm;
    private int mCurrentState;
    private float mProgress;
    private FrameLayout flProgress;
    private Button btn_download;
    private ProgressHorizontal pbprogress;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);
         btn_download = (Button)view.findViewById(R.id.btn_download);
         btn_download.setOnClickListener(this);
        //初始化自定义进度条
         flProgress=(FrameLayout)view.findViewById(R.id.fl_progress);
        flProgress.setOnClickListener(this);
         pbprogress=new ProgressHorizontal(UIUtils.getContext());
        pbprogress.setProgressBackgroundResource(R.drawable.progress_bg);//进度条背景
        pbprogress.setProgressDrawble(R.drawable.progress_normal);//进度条图片
        pbprogress.setProgressTextSize(UIUtils.dip2px(18));//进度文字的大小
        //宽高填充父窗体
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        //帧布局添加自定义进度条
        flProgress.addView(pbprogress,params);
         mDm= DownLoadManager.getInstance();
        //注册观察者 监听状态和进度变化
        mDm.registerObserver(new DownLoadManager.DownloadObserver() {

            //主线程或子线程
            public void onDownloadStateChanged(DownloadInfo info) {
                //判断下载对象是否是当前应用
                AppInfo appInfo=getData();
                if (appInfo.id.equals(info.id)){
                    refreshUIOnMainThread(info);
                }
            }

            //子线程
            public void onDownloadProgressChanged(DownloadInfo info) {
                //判断下载对象是否是当前应用
                AppInfo appInfo=getData();
                if (appInfo.id.equals(info.id)){
                    refreshUIOnMainThread(info);
                }
            }
        });
        return view;
    }

    //主线程更新Ui
    private void refreshUIOnMainThread(final DownloadInfo info){
        UIUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                refreshUI(info.currentState,info.getProgress());
            }
        });

    }

    @Override
    public void refreshView(AppInfo data) {
        //判断当前应用是否下载过
        DownloadInfo downloadInfo = mDm.getDownloadInfo(data);
        if (downloadInfo!=null){
            //之前下载过
             mCurrentState = downloadInfo.currentState;
             mProgress = downloadInfo.getProgress();
        }else {
            //没有下载过
            mCurrentState=DownLoadManager.STATE_UNDO;
            mProgress=0;
        }
        refreshUI(mCurrentState,mProgress);
    }

    //根据当前下载进度和状态来更新界面
    private void refreshUI(int currentState, float progress) {
        mCurrentState=currentState;
        switch (currentState){
            case DownLoadManager.STATE_UNDO://未下载
                flProgress.setVisibility(View.GONE);
                btn_download.setVisibility(View.VISIBLE);
                btn_download.setText("下载");
                break;
            case DownLoadManager.STATE_WAITING://等待下载
                flProgress.setVisibility(View.GONE);
                btn_download.setVisibility(View.VISIBLE);
                btn_download.setText("等待中...");
                break;
            case DownLoadManager.STATE_DOWNLOAD://正在下载
                flProgress.setVisibility(View.VISIBLE);
                btn_download.setVisibility(View.GONE);
                pbprogress.setCenterText("");
                pbprogress.setProgress(mProgress);//设置下载进度
                break;
            case DownLoadManager.STATE_PAUSE://下载暂停
                flProgress.setVisibility(View.VISIBLE);
                btn_download.setVisibility(View.GONE);
                pbprogress.setCenterText("暂停");
                pbprogress.setProgress(mProgress);
                break;
            case DownLoadManager.STATE_ERROR://下载失败
                flProgress.setVisibility(View.GONE);
                btn_download.setVisibility(View.VISIBLE);
                btn_download.setText("下载失败");
                break;
            case DownLoadManager.STATE_SUCCESS://下载成功
                flProgress.setVisibility(View.GONE);
                btn_download.setVisibility(View.VISIBLE);
                btn_download.setText("安装");
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_download:
            case R.id.fl_progress:
                //根据当前状态来决定下一步操作
                if (mCurrentState==DownLoadManager.STATE_UNDO||mCurrentState==DownLoadManager.STATE_ERROR
                        ||mCurrentState==DownLoadManager.STATE_PAUSE){
                    mDm.download(getData());//下载
                }else if (mCurrentState==DownLoadManager.STATE_DOWNLOAD||mCurrentState==DownLoadManager.STATE_WAITING){
                    mDm.pause(getData());//暂停下载
                }else if (mCurrentState==DownLoadManager.STATE_SUCCESS){
                    mDm.install(getData());//安装
                }
                break;
        }
    }

}
