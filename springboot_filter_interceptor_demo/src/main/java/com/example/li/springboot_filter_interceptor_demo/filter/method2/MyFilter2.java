package com.example.li.springboot_filter_interceptor_demo.filter.method2;

import javax.servlet.*;
import java.io.IOException;

/**
 * 方法2 ： 利用WebFilter、WebListener、WebServlet注解注册bean
 * 因为与方法一冲突，所以这里注释掉了注解，执行前取消Component、WebFilter、Order的注释
 * Component:
 * WebFilter、WebListener、WebServlet：
 * Order:有多个过滤器的时候，过滤器的执行顺序，数字越小，优先级越高；若无此注解，执行顺序由过滤器注册顺序
 *
 * @author Li
 * @date 2019/4/5-20:54
 * 过滤器继承 javax.servlet.Filter
 * Servlet继承 javax.servlet.http.HttpServlet;
 * 监听器继承 javax.servlet.ServletContextListener
 */

//@Component
//@WebFilter(urlPatterns = "/*", filterName = "myFilter2")
//@Order(value = 1)
public class MyFilter2 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*省略过滤方法*/

        //放行前
        System.out.println("方法2  放行前 过滤器1-----------------------");
        //放行
        filterChain.doFilter(servletRequest, servletResponse);
        //放行后
        System.out.println("方法2  放行后 过滤器1-----------------------");
    }

    @Override
    public void destroy() {

    }
}
