package com.example.li.springboot_filter_interceptor_demo.interceptor.conf;

import com.example.li.springboot_filter_interceptor_demo.interceptor.IPInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 官方文档：https://docs.spring.io/spring/docs/5.1.3.RELEASE/spring-framework-reference/web.html#mvc-config
 * @author Li
 * @date 2019/4/5-17:06
 */
//springboot版本为1.x时使用WebMvcConfigurerAdapter
//springboot版本为2.x时使用WebMvcConfigurerAdapter引用的接口WebMvcConfigurer
@Configuration
//@EnableWebMvc全局掌控mvc
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry);
        //浏览器发送 /atguigu 请求来到 success
        registry.addViewController("/").setViewName("index");

    }

    //配置interceptors
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IPInterceptor()).addPathPatterns("/user/**");
    }
}
