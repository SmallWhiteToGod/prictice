package com.prictice.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 苏博
 * @version V1.2.0
 * @className: EventStartPublish.java
 * @package com.prictice.event
 * @description: 发布开始事件
 * @date 2019/5/5 11:32
 */
@Component("eventStartPublisher")
public class EventStartPublish implements EventPublisher{

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void publish(String msg) throws InterruptedException{

        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName()+": 开始发布事件");
        applicationContext.publishEvent(new EventBean(msg));
    }
}