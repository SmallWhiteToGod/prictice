package com.prictice.javabase.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 苏博
 * @className: ThreadPoolTest.java
 * @package com.prictice.javabase.threadPool
 * @description: java中线程池的使用
 * @date 2019/10/27 12:47
 */
public class ThreadPoolTest {

    /**
     * 线程池按以下行为执行任务
     *     1. 当线程数小于核心线程数时，创建线程。
     *     2. 当线程数大于等于核心线程数，且任务队列未满时，将任务放入任务队列。
     *     3. 当线程数大于等于核心线程数，且任务队列已满
     *        若线程数小于最大线程数，创建线程
     *        若线程数等于最大线程数，抛出异常，拒绝任务
     */

    /**
     * FixedThreadPool 和 SingleThreadExecutor ： 允许请求的队列长度为 Integer.MAX_VALUE,可能堆积大量的请求，从而导致OOM。
     * CachedThreadPool 和 ScheduledThreadPool ： 允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致OOM。
     */

    /*ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue)*/

    /**
     * 固定线程数的线程池
     * ThreadPoolExecutor(nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
     * LinkedBlockingQueue() {this(Integer.MAX_VALUE);}
     */
    ExecutorService threadPool1 = Executors.newFixedThreadPool(100);


    /**
     * 只有一个线程的线程池
     * new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()))
     * LinkedBlockingQueue() {this(Integer.MAX_VALUE);}
     */
    ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

    /**
     * 可根据实际情况调整线程数量的线程池
     * ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
     * 当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize
     */
    ExecutorService threadPool3 = Executors.newCachedThreadPool();

    /**
     * 用来在给定的延迟后运行任务，或者定期执行任务
     * ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,new DelayedWorkQueue());
     */
    ExecutorService threadPool4 = Executors.newScheduledThreadPool(5);
    ExecutorService threadPool5 = Executors.newSingleThreadScheduledExecutor(); //corePollSize = 1



    /** 如何设置参数
     *     1、默认值
     *         * corePoolSize=1
     *         * queueCapacity=Integer.MAX_VALUE
     *         * maxPoolSize=Integer.MAX_VALUE
     *         * keepAliveTime=60s
     *         * allowCoreThreadTimeout=false
     *         * rejectedExecutionHandler=AbortPolicy()
     *
     *     2、如何来设置
     *         * 需要根据几个值来决定
     *             - tasks ：每秒的任务数，假设为500~1000
     *             - taskcost：每个任务花费时间，假设为0.1s
     *             - responsetime：系统允许容忍的最大响应时间，假设为1s
     *         * 做几个计算
     *             - corePoolSize = 每秒需要多少个线程处理？
     *                 * threadcount = tasks/(1/taskcost) =tasks*taskcout =  (500~1000)*0.1 = 50~100 个线程。corePoolSize设置应该大于50
     *                 * 根据8020原则，如果80%的每秒任务数小于800，那么corePoolSize设置为80即可
     *             - queueCapacity = (coreSizePool/taskcost)*responsetime
     *                 * 计算可得 queueCapacity = 80/0.1*1 = 800。意思是队列里的线程可以等待1s，超过了的需要新开线程来执行
     *                 * 切记不能设置为Integer.MAX_VALUE，这样队列会很大，线程数只会保持在corePoolSize大小，当任务陡增时，不能新开线程来执行，响应时间会随之陡增。
     *             - maxPoolSize = (max(tasks)- queueCapacity)/(1/taskcost)
     *                 * 计算可得 maxPoolSize = (1000-800)/10 = 20
     *                 * （最大任务数-队列容量）/每个线程每秒处理能力 = 最大线程数
     *             - rejectedExecutionHandler：根据具体情况来决定，任务不重要可丢弃，任务重要则要利用一些缓冲机制来处理
     *             - keepAliveTime和allowCoreThreadTimeout采用默认通常能满足
     *
     *     3、 以上都是理想值，实际情况下要根据机器性能来决定。如果在未达到最大线程数的情况机器cpu load已经满了，则需要通过升级硬件和优化代码，降低taskcost来处理。
     */
}


