package Utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import global.GooglePlayApplication;

public class UIUtils {
    public static Context getContext(){
        return GooglePlayApplication.getContext();
    }

     public static Handler getHandler(){
        return GooglePlayApplication.getHandler();
     }

     public static int getMainThreadId(){
        return GooglePlayApplication.getMainThreadId();
     }

     public static String getIpConfig(){
        return "http://192.168.2.4:8080/WebInfos/WebInfos/";
     }
     //获取字符串
     public static String getString(int id){
       return getContext().getResources().getString(id);
     }

     //获取字符串数组
     public static String[] getStringArray(int id){
        return getContext().getResources().getStringArray(id);
     }

     //获取图片
     public static Drawable getDrawable(int id){
        return getContext().getResources().getDrawable(id);
     }

     //获取颜色
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }

    //获取尺寸
    public static int getDimen(int id){
        return getContext().getResources().getDimensionPixelSize(id);//返回具体的像素值
    }

    //dp px转换
    public static int dip2px(float dip){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip*density+0.5f);
    }

    public static float px2dip(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return px/density;
    }

    //加载布局文件
    public static View inflate(int id){
        return View.inflate(getContext(),id,null);
    }

    //判断是否运行在主线程
    public static boolean isRunOnUIThread(){
        //获取当前线程id，如果当前id和主线程id相同就是主线程
        int myTid = Process.myTid();
        return myTid==getMainThreadId();
    }

    //运行在主线程的方法
    public static void runOnUIThread(Runnable runnable){
        if (isRunOnUIThread()){
            //主线程直接运行
            runnable.run();
        }else {
            //如果是子线程，借助handler让其运行在主线程
            getHandler().post(runnable);
        }
    }

    //根据id来获取颜色大状态选择器
    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        return getContext().getResources().getColorStateList(mTabTextColorResId);
    }
}
