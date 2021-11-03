package com.prictice.javabase.multiThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 苏博
 * @className: CountDownLatchTest.java
 * @package com.prictice.javabase.multiThread
 * @description:
 * @date 2019/9/16 10:30
 */
public class CountDownLatchTest {

    final static int NUM = 5;
    final static CountDownLatch countDownLatch = new CountDownLatch(NUM);
    final static ExecutorService threadPool = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws InterruptedException{

        for (int i = 0; i < 5; i++) {
            final int threadnum = i;
            threadPool.execute(()->{
                try {
                    MutiClient.test(threadnum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        threadPool.shutdown();
        System.out.println("finish");
    }
}
