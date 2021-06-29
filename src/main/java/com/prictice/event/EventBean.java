package com.prictice.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author 苏博
 * @version V1.2.0
 * @company lhfinance.com
 * @className: EventBean.java
 * @package com.prictice.event
 * @description: 自定义事件
 * @date 2019/5/5 11:24
 */
public class EventBean extends ApplicationEvent {

    private String message;

    public EventBean(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}