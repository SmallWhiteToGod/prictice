package com.prictice.javabase.multiThread;

/**
 * @author 苏博
 * @className: MutiClient.java
 * @package com.prictice.javabase.multiThread
 * @description:
 * @date 2019/9/12 11:24
 *
 *
 * 一、普通开启异步线程
 *
 * new Thread(() -> System.out.println("--" + "aaa")).start();1
 * 二、线程池开启异步线程（不接收返回参数）
 *
 * public static ExecutorService executor = Executors.newFixedThreadPool(10);
 * executor.submit(() -> aiCollectionFacade.initAiCollection(dto));
 * 1234
 * 三、线程池开启异步线程（接收返回参数）
 *
 * public static ExecutorService executor = Executors.newFixedThreadPool(10);
 * Future<?> result = executor.submit(() -> sum(a, b));
 * System.out.println(result.get());
 */
public class MutiClient {

    public static void main(String[] args) throws Exception{

        SaveOrUnSave a = new SaveOrUnSave();
        for (int i = 0; i < 5; i++) {
            new Thread(a).start();
        }

        Thread.sleep(5000);
        System.out.println(a.getNum());
        System.out.println(a.getAtomicInteger());
    }



    public static void test(int threadnum) throws InterruptedException {
        // 模拟请求的耗时操作
        Thread.sleep(1000);
        System.out.println("threadnum:" + threadnum);
        // 模拟请求的耗时操作
        Thread.sleep(2000);
    }
}
