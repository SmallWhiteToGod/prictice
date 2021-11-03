package com.prictice.event;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author 苏博
 * @version V1.2.0
 * @className: EventPublisher.java
 * @package com.prictice.event
 * @description: 发布事件接口
 * @date 2019/5/5 11:32
 */
public interface EventPublisher {

    void publish(String msg) throws InterruptedException;
}
