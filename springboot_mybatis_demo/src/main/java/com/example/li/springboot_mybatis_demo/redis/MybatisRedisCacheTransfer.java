package com.example.li.springboot_mybatis_demo.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MybatisRedisCacheTransfer {

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        MybatisRedisCache.setRedisTemplate(redisTemplate);
    }

}
