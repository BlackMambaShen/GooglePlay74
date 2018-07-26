package com.example.liang.googleplay74.manager;

import android.content.Intent;
import android.net.Uri;

import com.example.liang.googleplay74.domain.AppInfo;
import com.example.liang.googleplay74.domain.DownloadInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import Utils.UIUtils;

/*
下载管理器
DownLoadManager:被观察者 有责任通知所有观察者状态和进度发生变化
 */
public class DownLoadManager {

    public static final int STATE_UNDO=1;
    public static final int STATE_WAITING=2;
    public static final int STATE_DOWNLOAD=3;
    public static final int STATE_PAUSE=4;
    public static final int STATE_ERROR=5;
    public static final int STATE_SUCCESS=6;
    private static  DownLoadManager mDM=new DownLoadManager();
    //4.观察者集合
    private ArrayList<DownloadObserver> mObservers=new ArrayList<DownloadObserver>();

    //下载对象的集合
    private ConcurrentHashMap<String,DownloadInfo>mDownloadInfoMap=new ConcurrentHashMap<String,DownloadInfo>();

    private ConcurrentHashMap<String,DownloadTask>mDownloadTaskMap=new ConcurrentHashMap<String,DownloadTask>();

    private DownLoadManager(){

    }
    public static DownLoadManager getInstance(){
        return mDM;
    }

    //2.注册观察者
    public void registerObserver(DownloadObserver observer){
        if (observer!=null&&!mObservers.contains(observer)){
            mObservers.add(observer);
        }
    }

    //3.注销观察者
    public void unregisterObserver(DownloadObserver observer){
        if (observer!=null&&mObservers.contains(observer)){
            mObservers.remove(observer);
        }
    }

    //5.通知下载状态发生变化
    public void notifyDownloadStateChanged(DownloadInfo info){
        for (DownloadObserver observer:mObservers) {
            observer.onDownloadStateChanged(info);
        }
    }

    //6.通知下载进度发生变化
    public void notifyDownloadProgressChanged(DownloadInfo info){
        for (DownloadObserver observer:mObservers) {
            observer.onDownloadProgressChanged(info);
        }
    }
    /*
    1.声明观察者的接口
     */
    public interface DownloadObserver{
        //下载状态发生变化
        public void onDownloadStateChanged(DownloadInfo info);
        //下载进度发生变化
        public void onDownloadProgressChanged(DownloadInfo info);
    }

    //开始下载
    public synchronized void download(AppInfo info){
        //如果对象是第一次下载，需要创建一个新的downloadInfo对象从头下载
        //如果之前下载过，要接着下载，要实现断点续传
        DownloadInfo downloadInfo = mDownloadInfoMap.get(info.id);
        if (downloadInfo==null){
             downloadInfo = DownloadInfo.copy(info);//生成一个下载对象
        }
        downloadInfo.currentState=STATE_WAITING;//状态切换成等待下载
        notifyDownloadStateChanged(downloadInfo);//通知所有观察者,状态发生变化了
        //将下载对象放入集合中
        mDownloadInfoMap.put(downloadInfo.id,downloadInfo);
        //初始化下载任务，并放入线程池中运行
        DownloadTask task=new DownloadTask(downloadInfo);
        ThreadManager.getThreadPool().execute(task);
        //将下载任务放入集合中
        mDownloadTaskMap.put(downloadInfo.id,task);
    }

    //下载任务对象
    class DownloadTask implements Runnable{
        private DownloadInfo downloadInfo;
        public DownloadTask(DownloadInfo downloadInfo){
            this.downloadInfo=downloadInfo;
        }
        @Override
        public void run() {
            downloadInfo.currentState=STATE_DOWNLOAD;
            notifyDownloadStateChanged(downloadInfo);
            File file=new File(downloadInfo.path);
            if (!file.exists()||file.length()!=downloadInfo.currentPos
                    ||downloadInfo.currentPos==0){
                //从头开始下载
                //删除无效文件
                file.delete();//文件如果不存在也是可以删除的
                downloadInfo.currentPos=0;//当前下载位置置为0

            }else {
                //断点续传
                file.delete();//删除无效文件
                downloadInfo.currentState=STATE_ERROR;
                downloadInfo.currentPos=0;
                notifyDownloadStateChanged(downloadInfo);
            }
            //文件下载结束
            if (file.length()==downloadInfo.size){
                //文件完整，下载成功
                downloadInfo.currentState=STATE_SUCCESS;
                notifyDownloadStateChanged(downloadInfo);
            }else if (downloadInfo.currentState==STATE_PAUSE){
                //中途暂停
                notifyDownloadStateChanged(downloadInfo);
            }else {
                //下载失败
                file.delete();//删除无效文件
                downloadInfo.currentState=STATE_ERROR;
                downloadInfo.currentPos=0;
                notifyDownloadStateChanged(downloadInfo);
            }
            //从集合中移除下载任务
            mDownloadTaskMap.remove(downloadInfo.id);
        }
    }

    //下载暂停
    public synchronized void pause(AppInfo info){
        //取出下载对象
        DownloadInfo downloadInfo = mDownloadInfoMap.get(info.id);
        //只有正在下载和等待下载时才需要暂停
        if (downloadInfo!=null){
            if (downloadInfo.currentState==STATE_DOWNLOAD||downloadInfo.currentState==STATE_WAITING){
                downloadInfo.currentState=STATE_PAUSE;
                notifyDownloadStateChanged(downloadInfo);
                DownloadTask task = mDownloadTaskMap.get(downloadInfo.id);
                if (task!=null){
                    //移除下载任务
                    ThreadManager.getThreadPool().cancel(task);
                }
            }
        }

    }

    //开始安装
    public void install(AppInfo info){
        DownloadInfo downloadInfo = mDownloadInfoMap.get(info.id);
        if (downloadInfo!=null){
            //跳到系统安装页面
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://"+downloadInfo.path),
                    "application/vnd.android.package.archive");
            UIUtils.getContext().startActivity(intent);
        }
    }

    //根据应用信息返回下载对象
    public DownloadInfo getDownloadInfo(AppInfo info){
        return mDownloadInfoMap.get(info.id);
    }
}
