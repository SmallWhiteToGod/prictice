package com.prictice.javabase.threadPool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: PositiveExecutorsDemo.java
 * @package com.prictice.javabase.threadPool
 * @description:
 * @date 2019/10/27 14:10
 */
public class PositiveExecutorsDemo implements Runnable{

    private Integer count = 0;

    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();

    private static ExecutorService pool = new ThreadPoolExecutor(5, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(20), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {

        PositiveExecutorsDemo instance = new PositiveExecutorsDemo();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                pool.execute(instance);
            }catch (RejectedExecutionException e){
                e.printStackTrace();
                System.out.println();
                System.exit(1);
            }
        }
    }

    @Override
    public void run() {
        synchronized (PositiveExecutorsDemo.class){
            System.out.println(count++);
        };
    }
}
