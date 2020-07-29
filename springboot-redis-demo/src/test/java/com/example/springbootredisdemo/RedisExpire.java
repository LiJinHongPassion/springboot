package com.example.springbootredisdemo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * redis的值的过期时间的基本操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisExpire {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //为键值对设置过期时间
    @Test
    public void set(){
        //添加键值name: tom, 设置的是10秒失效，十秒之内查询有结果，十秒之后返回为null
        redisTemplate.opsForValue().set("name","tom",10, TimeUnit.SECONDS);

        try {
            //10秒内
            System.out.println(redisTemplate.opsForValue().get("name"));
            Thread.sleep(10000);
            //10秒后
            System.out.println(redisTemplate.opsForValue().get("name"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}