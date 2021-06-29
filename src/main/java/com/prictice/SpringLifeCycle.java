package com.prictice;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;


import javax.annotation.PostConstruct;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: SpringLifeCirCle.java
 * @package com.prictice
 * @description:
 * @date 2019/12/20 9:43
 */
public class SpringLifeCycle implements InitializingBean, BeanPostProcessor {

    SpringLifeCycle(){
        System.out.println("构造方法");
    }

    @PostConstruct
    private void postConstruct(){
        System.out.println("@PostConstruct 注解");
    }


    public void initMethod(){
        System.out.println("initMethod");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean: afterPropertiesSet");
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("BeanPostProcessor : postProcessBeforeInitialization");
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("BeanPostProcessor : postProcessAfterInitialization");
        return null;
    }

    private void destroyMethod(){
        System.out.println("destroyMethod");
    }


}
