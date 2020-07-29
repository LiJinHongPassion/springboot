package com.example.springbootredisdemo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * redis的基本增删改查操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCRUD {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //添加( 前提是添加的key值在redis里面 不存在 )
    @Test
    public void set(){
        redisTemplate.opsForValue().set("myKey","myAdd");
        System.out.println(redisTemplate.opsForValue().get("myKey"));
    }

    //查看
    @Test
    public void get(){
        //当key不存在, 则为null
        System.out.println(redisTemplate.opsForValue().get("myKey"));
    }

    //更新( 前提是更新的key值在redis里面 存在 )
    @Test
    public void update(){
        redisTemplate.opsForValue().set("myKey","myUpdate");
        System.out.println(redisTemplate.opsForValue().get("myKey"));
    }

    //删除
    @Test
    public void remove(){
        redisTemplate.delete("myKey");
        System.out.println(redisTemplate.opsForValue().get("myKey"));
    }

}