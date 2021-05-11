package com.example.springbootbeanlifedemo.config.beanPostProcessor;

import com.example.springbootbeanlifedemo.bean.MyBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijinhong
 * @date 2021年05月11日 08:56
 * @Description:
 */
@Configuration
public class MySecondBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof MyBean){
            System.out.println("BeanPostProcessor -- 2 -- 初始化前的处理\n↓");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof MyBean){
            System.out.println("BeanPostProcessor -- 2 -- 初始化后的处理\n");
        }
        return bean;
    }
}
