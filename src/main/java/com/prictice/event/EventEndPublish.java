package com.prictice.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 苏博
 * @version V1.2.0
 * @company lhfinance.com
 * @className: EventEndPublish.java
 * @package com.prictice.event
 * @description: 发布结束事件
 * @date 2019/5/5 11:33
 */
@Component("eventEndPublish")
public class EventEndPublish implements EventPublisher{

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void publish(String msg) throws InterruptedException{

        TimeUnit.SECONDS.sleep(1);
        System.out.println(Thread.currentThread().getName()+": 发布结束事件");
        applicationContext.publishEvent(new EventBean(msg));

    }
}