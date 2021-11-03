package com.prictice.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 苏博
 * @version V1.2.0
 * @className: EventListener.java
 * @package com.prictice.event
 * @description: 自定义事件监听
 * @date 2019/5/5 11:39
 */
@Component
public class MyEventListener {

    /**
     * @Async 表示支持异步调用
     * @EventListener 可以用实现接口ApplicationListener，来代替
     */
    @Async
    @EventListener(classes = EventBean.class)
    public void onApplicationEvent(ApplicationEvent event) throws Exception{
        if(event instanceof EventBean){
            EventBean eventBean = (EventBean)event;
            System.out.println(Thread.currentThread().getName()+": 异步事件监听成功, "+eventBean.getMessage());
        }
        try {
            TimeUnit.SECONDS.sleep(2);
            if(Math.random()>0.99){
                throw new EventException("事件处理异常");
            }
            System.out.println(Thread.currentThread().getName()+": 事件处理完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (EventException e){
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName()+": 事件处理异常");
            throw new Exception("报错了小伙子");
        }finally {
            //System.exit(0);
        }
    }
}