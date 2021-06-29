package com.prictice.javabase.multiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: SemaphoreTest.java
 * @package com.prictice.javabase.multiThread
 * @description: 信号量 测试类
 * @date 2019/9/16 10:01
 */
public class SemaphoreTest {

    /**
     * synchronized 和 ReentrantLock 都是一次只允许一个线程访问某个资源，
     * Semaphore(信号量)可以指定多个线程同时访问某个资源
     * @param args
     */
    public static void main(String[] args) throws InterruptedException{
        // 创建一个具有固定线程数量的线程池对象（如果这里线程池的线程数量给太少的话你会发现执行的很慢）
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        // 一次只能允许执行的线程数量。
        final Semaphore semaphore = new Semaphore(20);

        for (int i = 0; i < 100; i++) {
            final int threadnum = i;
            threadPool.execute(() -> {// Lambda 表达式的运用
                try {
                    // 获取一个许可，所以可运行线程数量为20/1=20
                    semaphore.acquire();
                    MutiClient.test(threadnum);
                    // 释放一个许可
                    semaphore.release();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            });
        }
        threadPool.shutdown();
        System.out.println("finish");
    }

}
