package NetWork;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by duchaoqiang on 2017/1/6.
 * 线程池的管理
 */
public class ThreadManager {
    private  static ThreadPool mThreadPool;
    //获取线程池对象
    public static synchronized ThreadPool getThreadPool(){
        if (mThreadPool==null){
            int cpuNum = Runtime.getRuntime().availableProcessors();// 获取设备的cpu个数
            int count = cpuNum * 2 + 1; //线程池中线程数的最大值
            mThreadPool = new ThreadPool(count, count, 0L);
        }
        return mThreadPool;
    }

    public static class ThreadPool{
        private int corePoolSize;// 核心线程数
        private int maximumPoolSize;// 最大线程数
        private long keepAliveTime;// 保持活跃时间(休息时间)
        private ThreadPoolExecutor executor;
        private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }
        //执行任务
        public void execute(Runnable runnable){
          if (executor==null){
              /***
               * corePoolSize 核心线程数
               * maximumPoolSize 最大线程数
               * keepAliveTime 休息时间
               * TimeUnit.SECONDS 线程的活跃单位
               * new LinkedBlockingDeque<Runnable>() 线程队列
               * Executors.defaultThreadFactory() 生产线程的工厂
               * new ThreadPoolExecutor.AbortPolicy() 线程异常处理策略
               */
              executor=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
          }
            //线程池执行传进来的线程
            executor.execute(runnable);
        }
        public void cancel(Runnable runnable){
            if (executor!=null){
                executor.getQueue().remove(runnable);//把线程移除队列
            }
        }
    }

}
