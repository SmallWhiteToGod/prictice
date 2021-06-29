package com.prictice.javabase.multiThread;

import org.junit.jupiter.api.Test;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: SleepAndWait.java
 * @package com.prictice.javabase.multiThread
 * @description: sleep 和 wait方法
 * @date 2020/2/17 12:02
 */
public class SleepAndWaitTest {

    /**
     * sleep 是Thread的方法 对线程操作 不释放锁
     * 需要try catch 被其他线程interrupt()中断时候执行
     */
    class Sleep implements Runnable{
        @Override
        public void run() {
            System.out.println("thread1 begin sleep");
            try {
                Thread.sleep(7000);
                System.out.println("thread1 begin wake up");
            } catch (InterruptedException e) {
                System.out.println("thread1 has been interrupted");
            }
        }
    }

    class Wait implements Runnable{
        @Override
        public void run() {
            try {
                wait();
                System.out.println("hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private Thread thread1 = new Thread(new Sleep());


    //线程1 sleep 2秒钟
    public void sleep1(){
        thread1.start();
    }

    //打断线程1
    public void interrupt1(){
        if (!thread1.isInterrupted()){
            //如果线程1 不是终断的
            System.out.println("thread1 was not interrupted, try interrup it");
            thread1.interrupt();
        }else {
            System.out.println("thread1 was isInterrupted");
        }
    }


    @Test
    public void sleepTest() throws Exception{
        SleepAndWaitTest sleepAndWait = new SleepAndWaitTest();
        sleepAndWait.sleep1();
        Thread.sleep(3000);
        sleepAndWait.interrupt1();
    }

    public static void main(String[] args) throws Exception{



    }
}
