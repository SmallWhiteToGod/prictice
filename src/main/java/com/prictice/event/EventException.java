package com.prictice.event;

/**
 * @author 苏博
 * @version V1.2.0
 * @className: EventException.java
 * @package com.prictice.event
 * @description: 事件异常
 * @date 2019/5/5 16:54
 */
public class EventException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public String errorCode;

    public EventException() {
    }

    public EventException(Throwable cause) {
        super(cause);
    }

    public EventException(String message) {
        super(message);
    }

    public EventException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public EventException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
