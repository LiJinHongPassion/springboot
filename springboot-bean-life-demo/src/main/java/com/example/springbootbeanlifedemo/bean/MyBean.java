package com.example.springbootbeanlifedemo.bean;

import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.*;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import javax.annotation.PreDestroy;

/**
 * @author lijinhong
 * @date 2021年05月11日 08:53
 * @Description:
 */
public class MyBean implements
        BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, ApplicationEventPublisherAware ,
        MessageSourceAware, ApplicationContextAware,//Aware相关接口
        InitializingBean, DisposableBean {

    private String myBeanDetail;
    private String inner;

    public String getMyBeanDetail() {
        return myBeanDetail;
    }

    public void setMyBeanDetail(String myBeanDetail) {
        System.out.println("bean基础信息 -- 设置myBeanDetail属性");
        this.myBeanDetail = myBeanDetail;
    }

    public String getInner() {
        return inner;
    }

    public void setInner(String inner) {
        System.out.println("bean基础信息 -- 设置inner属性");
        this.inner = inner;
    }

    @Override
    public void setBeanName(String s) {

        System.out.println("--------------------------------------------------");
        System.out.println("MyBean");
        System.out.println("--------------------------------------------------");
        System.out.println();
        System.out.println("\tBeanNameAware -- setBeanName -- 设置beanName\n\t↓");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("\tBeanClassLoaderAware -- setBeanClassLoader -- 设置bean的类加载器\n\t↓");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("\tBeanFactoryAware -- setBeanFactory -- 设置bean的工厂\n\t↓");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        System.out.println("\tApplicationEventPublisherAware -- setApplicationEventPublisher -- 发布事件\n\t↓");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        System.out.println("\tMessageSourceAware -- setMessageSource -- 获取 Message Source 相关文本信息\n\t↓");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("\tApplicationContextAware -- setApplicationContext -- 设置应用上下文\n↓");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("\tInitializingBean -- 被BeanFactory在设置所有bean属性之后调用 -- 初始化bean\n↓");
    }

    public void init() {
        System.out.println("\tinit -- init -- init bean\n↓");
    }

    /*****************************************销毁*****************************************/

    @PreDestroy
    public void preDestroy() throws Exception {
        System.out.println("--------------------------------------------------");
        System.out.println("MyBean");
        System.out.println("--------------------------------------------------");
        System.out.println();
        System.out.println("\nPreDestroy -- 销毁bean之前\n");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("\nDisposableBean -- 销毁bean\n");
    }

    public void beanDestroy() throws Exception {
        System.out.println("\ndestroy-method -- beanDestroy\n");
    }

    /**
     * Object的方法，对象在垃圾回收前最后的操作
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("------inside finalize-----");
    }

}
