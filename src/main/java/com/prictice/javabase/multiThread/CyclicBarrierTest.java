package com.prictice.javabase.multiThread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: CyclicBarrierTest.java
 * @package com.prictice.javabase.multiThread
 * @description:
 * @date 2019/9/16 11:33
 */
public class CyclicBarrierTest {

    final static int NUM = 5;

    //到达后优先执行，可不配置
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(NUM, () -> {
        System.out.println("------当线程数达到之后，优先执行------");
    });

    public static void main(String[] args) throws Exception{
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < NUM ; i++) {
            final int threadnum = i;
            threadPool.execute(()->{
                try {
                    test(threadnum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        threadPool.shutdown();
        System.out.println("finish");
    }

    private static void test(int num) throws Exception{
        System.out.println("thread: "+ num+" start");
        Thread.sleep(112000);
        cyclicBarrier.await();
        System.out.println("thread: "+ num+" finish");

    }
}
