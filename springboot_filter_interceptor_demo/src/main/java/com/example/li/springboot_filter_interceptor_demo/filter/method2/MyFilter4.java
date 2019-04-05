package com.example.li.springboot_filter_interceptor_demo.filter.method2;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author Li
 * @date 2019/4/5-20:54
 * 过滤器一定要继承Filter
 */
//@Component
//@WebFilter(urlPatterns = "/*", filterName = "myFilter4")
//@Order(value = 2)
public class MyFilter4 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*省略过滤方法*/

        //放行前
        System.out.println("方法2 放行前 过滤器4-----------------------");
        //放行
        filterChain.doFilter(servletRequest, servletResponse);
        //放行后
        System.out.println("方法2 放行后 过滤器4-----------------------");
    }

    @Override
    public void destroy() {

    }
}
