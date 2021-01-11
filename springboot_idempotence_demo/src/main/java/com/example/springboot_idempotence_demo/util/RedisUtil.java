package com.example.springboot_idempotence_demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 21.1.11
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //如果存在key，则不添加；不存在就添加
    public Boolean set(Integer orderId, String token){
        return redisTemplate.opsForValue().setIfAbsent(String.valueOf(orderId), token);
    }

    //获取并删除 -- 需要保证原子性
    public Boolean get(Integer orderId){
        return redisTemplate.delete(String.valueOf(orderId));
    }

}
