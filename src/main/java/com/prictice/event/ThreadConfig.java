package com.prictice.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * @author 苏博
 * @version V1.2.0
 * @className: ThreadConfig.java
 * @package com.prictice.event
 * @description: 线程池配置 注意使用@EnableAsync注解
 * @date 2019/5/5 14:38
 */
@Configuration
@EnableAsync
public class ThreadConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(40);
        executor.setThreadNamePrefix("ThreadPoolTaskExecutor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(300);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new DefaultAsyncExceptionHandle();
    }

    class DefaultAsyncExceptionHandle implements AsyncUncaughtExceptionHandler {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            System.out.println(method.getDeclaringClass().getSimpleName()+"==111===");
            logger.error("fatal error: 异步线程执行[{}]方法, 参数[{}], 异常信息:[{}]",
                    method.getDeclaringClass().getSimpleName(), Arrays.toString(objects), throwable.getMessage());
            throwable.printStackTrace();
        }
    }
}
