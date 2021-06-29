package com.prictice.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author 苏博
 * @version V1.2.0
 * @company lhfinance.com
 * @className: TestEvent.java
 * @package com.prictice.event
 * @description:  测试事件
 * @date 2019/5/5 11:19
 */
public class TestEvent {

    private static ApplicationContext context = null;

    public static void main(String[] args) throws Exception{
        // 使用config注解配置context
        // AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
        context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Long start = System.currentTimeMillis();

        EventPublisher eventStart= (EventPublisher)context.getBean("eventStartPublisher");
        EventPublisher eventEnd= (EventPublisher)context.getBean("eventStartPublisher");
        eventStart.publish("这是一个开始事件");
        eventEnd.publish("这是一个结束事件");
        System.out.printf(Thread.currentThread().getName()+": 异步测试耗时[%d]毫秒 \n",System.currentTimeMillis()-start);

    }
}
