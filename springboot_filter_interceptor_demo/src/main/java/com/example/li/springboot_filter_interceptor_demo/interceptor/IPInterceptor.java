package com.example.li.springboot_filter_interceptor_demo.interceptor;


import com.example.li.springboot_filter_interceptor_demo.dao.BlackListDao;
import com.example.li.springboot_filter_interceptor_demo.entity.BlackList;
import com.example.li.springboot_filter_interceptor_demo.util.IPAddressUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Li
 * @date 2019/4/5-18:35
 * 查看 *HandlerInterceptor*接口方法
 */
public class IPInterceptor implements HandlerInterceptor {

//    @Resource
    private BlackListDao blackListDao = new BlackListDao();

    private Map<String, Integer> redisTemplate = new HashMap<String, Integer>();
    private static final Logger logger = LoggerFactory.getLogger(IPInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String ip = IPAddressUtil.getClientIpAddress(httpServletRequest);
        BlackList blackList = blackListDao.findByIp(ip);
        if (blackList == null ){
            urlHandle(httpServletRequest, 5000, 5);
        } else {
            modelAndView.setViewName("/errorpage/error");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void urlHandle(HttpServletRequest request, long limitTime,int limitCount) {
        try {
            System.out.println("拦截器！！！");
            String ip = IPAddressUtil.getClientIpAddress(request);
            String url = request.getRequestURL().toString();
            final String key = "req_limit_".concat(url).concat(ip);

            if(redisTemplate.get(key)==null || redisTemplate.get(key)==0){
                redisTemplate.put(key,1);
            }else{
                redisTemplate.put(key,redisTemplate.get(key)+1);
            }
            int count = redisTemplate.get(key);
            if (count > 0) {
                Timer timer= new Timer();
                TimerTask task  = new TimerTask(){
                    @Override
                    public void run() {
                        redisTemplate.remove(key);
                    }
                };
                timer.schedule(task, limitTime);
            }
            if (count > limitCount){
                Calendar calendar = Calendar.getInstance();
                Date iptime=calendar.getTime();
                BlackList blackList = new BlackList(ip, iptime);
                blackListDao.addBlackList(blackList);
                logger.warn("HTTP请求超出设定的限制!!!!");
            }
        }  catch (Exception e) {
            logger.error("发生异常: ", e);
        }
    }

}
