package com.example.liang.googleplay74.domain;

import android.os.Environment;

import com.example.liang.googleplay74.manager.DownLoadManager;

import java.io.File;

/*
下载对象
注意：
 */
public class DownloadInfo {
    public String id;
    public String name;
    public String downloadUrl;
    public long size;
    public String packageName;
    public long currentPos;//当前下载位置
    public int currentState;//当前下载状态
    public String path;//下载的本地文件的路径
    public static final String GOOGLE_MARKET="GOOGLE_MARKET";//sd卡根目录文件夹名称
    public static final String DOWNLOAD="DOWNLOAD";//子文件夹名称，存放下载的文件
    //获取下载进度（0-1）
    public float getProgress(){
        if (size==0){
            return 0;
        }
       float progress= currentPos/(float)size;
       return progress;
    }

    //获取文件下载路径
    public String getFilePath(){
        StringBuffer sb=new StringBuffer();
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        sb.append(sdcard);
        sb.append(File.separator);
        sb.append(GOOGLE_MARKET);
        sb.append(File.separator);
        sb.append(DOWNLOAD);
        if (createDir(sb.toString())){
            //文件夹存在或者已经创建完成
            return sb.toString()+File.separator+name+".apk";//返回文件具体路径
        }
        return null;
    }

    private boolean createDir(String dir){
        File dieFile=new File(dir);

        //文件夹不存在或者不是一个文件夹
        if (!dieFile.exists()||!dieFile.isDirectory()){
            return dieFile.mkdirs();
        }
        return true;//文件夹存在
    }

    //拷贝对象 从appInfo拷贝出一个downloadInfo对象
    public static DownloadInfo copy(AppInfo info){
        DownloadInfo downloadInfo=new DownloadInfo();
        downloadInfo.id=info.id;
        downloadInfo.name=info.name;
        downloadInfo.downloadUrl=info.downloadUrl;
        downloadInfo.size=info.size;
        downloadInfo.currentPos=0;
        downloadInfo.currentState= DownLoadManager.STATE_UNDO;//默认状态未下载
        downloadInfo.path=downloadInfo.getFilePath();
        return downloadInfo;
    }
}
