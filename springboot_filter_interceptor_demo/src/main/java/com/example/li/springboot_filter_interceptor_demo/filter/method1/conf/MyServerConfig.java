package com.example.li.springboot_filter_interceptor_demo.filter.method1.conf;

import com.example.li.springboot_filter_interceptor_demo.filter.method1.MyFilter1;
import com.example.li.springboot_filter_interceptor_demo.filter.method1.MyFilter3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;

/**
 * 方法1 ： 通过Configuration注解和@bean注解注册bean，实现Servlet、Filter、Listener三大组件的注册
 * 官方文档：https://docshome.gitbooks.io/springboot/content/pages/spring-boot-features.html#boot-features-embedded-container-servlets-filters-listeners
 * @author Li
 * @date 2019/4/5-20:33
 */

@Configuration
public class MyServerConfig {

//    过滤器一定要继承Filter
    @Bean
    public Filter getMyFilter1(){
        return new MyFilter1();
    }
    @Bean
    public Filter getMyFilter3(){
        return new MyFilter3();
    }

    /**
     * registrationBean.setOrder(1);若无此注解，执行顺序由过滤器注册顺序
     * registrationBean.setUrlPatterns(Arrays.asList("/index","/*"));设置过滤的路径{"/index","/*"}
     * registrationBean.setFilter(getMyFilter1());设置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean myFilter1RegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(getMyFilter1());
        registrationBean.setUrlPatterns(Arrays.asList("/index","/*"));
        registrationBean.setOrder(1);
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean myFilter3RegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(getMyFilter3());
        registrationBean.setUrlPatterns(Arrays.asList("/index","/*"));
        registrationBean.setOrder(2);
        return registrationBean;
    }

}
