package com.example.springbootbeanlifedemo.config.beanFactoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijinhong
 * @date 2021年05月11日 09:46
 * @Description:
 */
@Configuration
public class MySecondBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("BeanFactoryPostProcessor -- 2 -- 进入容器前可提前对bean的定义(配置元数据)进行处理\n↓");
    }
}
