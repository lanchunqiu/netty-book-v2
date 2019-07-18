package com.lancq.netty.pio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 *
 * @author lancq
 */
public class TimeServerHandlerExecutePool {

    private ExecutorService executor;

    /**
     * 构造方法
     *
     * @param maxPoolSize 最大线程数
     * @param queueSize 队列大小
     */
    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        System.out.println("初始化线程池，maxPoolSize = " + maxPoolSize + ", queueSize = " + queueSize);
        this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }
}
