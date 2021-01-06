package per.codeant.filter;

import io.jsonwebtoken.Claims;
import per.codeant.util.JwtService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LJH
 * @date 2019/7/16-6:16
 * @QQ 1755497577
 */
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request =(HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("authorization"); //获取请求传来的token
        if( token == null){
            response.getWriter().write("请携带token");
            return;
        }
        Claims claims = JwtService.parsePersonJWT(token); //验证token
        if (claims == null) {
            response.getWriter().write("请携带token");
        }else {
            //授权通过
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}