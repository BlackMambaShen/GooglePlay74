package com.example.liang.googleplay74.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
线程管理器
 */
public class ThreadManager {

    private static ThreadPool mThreadPool;
    public static ThreadPool getThreadPool(){
        if (mThreadPool==null){
            synchronized (ThreadManager.class){
                if (mThreadPool==null){
                    int cpuCount = Runtime.getRuntime().availableProcessors();
//                    int threadCount=cpuCount*2+1;//线程个数
                    int threadCount=10;
                    mThreadPool=new ThreadPool(threadCount, threadCount,0);
                }
            }
        }
        return mThreadPool;
    }

    //线程池
    public static class ThreadPool{

        private int corePoolSize;//核心线程数
        private int maxmumPoolSize;//最大线程数
        private long keepAliveTime;//休息时间
        private ThreadPoolExecutor executor;

        private ThreadPool(int corePoolSize,int maxmumPoolSize,long keepAliveTime){
            this.corePoolSize=corePoolSize;
            this.maxmumPoolSize=maxmumPoolSize;
            this.keepAliveTime=keepAliveTime;
        }

        public void execute(Runnable r){
            /*
            参一：核心线程数
            2.最大线程数
            3.线程休眠时间
            4.时间单位
            5.线程队列
            6.生成线程的工厂
            7.线程异常处理策略
             */
            if (executor==null){
                executor=new ThreadPoolExecutor(corePoolSize,
                        maxmumPoolSize,keepAliveTime, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<Runnable>(), Executors.defaultThreadFactory()
                        ,new ThreadPoolExecutor.AbortPolicy());
            }
            //线程池执行一个runnable对象,具体运行时机线程池说了算
            executor.execute(r);
        }

        //取消任务
        public void cancel(Runnable r){
            if (executor!=null){
                //从线程队列中移除对象
                executor.getQueue().remove(r);
            }
        }
    }
}
