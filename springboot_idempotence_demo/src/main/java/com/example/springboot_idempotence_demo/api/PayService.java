package com.example.springboot_idempotence_demo.api;

import com.example.springboot_idempotence_demo.util.RandomString;
import com.example.springboot_idempotence_demo.util.RedisUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 21.1.11
 */
@Service
public class PayService {

    @Autowired
    private RedisUtil redisUtil;

    public String getToken() {
        String token = RandomString.getRandomString();
        if(redisUtil.set(31, token)){
            return token;
        }
        return "";
    }

    public String pay(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (!parameterMap.containsKey("token")
                || StringUtil.isNullOrEmpty(Arrays.toString(parameterMap.get("token")))
        ){
            return "非法请求";
        }

        if (!redisUtil.get(31)){
            return "请勿重复支付";
        }else {
            //...
            //结账流程
            if (currentTimeMillis()%2 == 1){
                return "结账成功";
            }else {
                return "结账失败";
            }
        }
    }
}
