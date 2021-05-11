package com.example.springbootbeanlifedemo.config;

import com.example.springbootbeanlifedemo.bean.MyBean;
import com.example.springbootbeanlifedemo.bean.MySecondBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijinhong
 * @date 2021年05月11日 13:35
 * @Description:
 */
@Configuration
public class BeanConfig {

    @Bean(initMethod="init", destroyMethod="beanDestroy")
    public MySecondBean MySecondBean(){
        return new MySecondBean();
    }


    @Bean(initMethod="init", destroyMethod="beanDestroy")
    public MyBean myBean(){
        return new MyBean();
    }
}
