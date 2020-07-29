package com.example.springbootredisdemo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

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

    //通过正则表达式获取所有符合规则的key值
    @Test
    public void getMoreKeys(){
        redisTemplate.opsForValue().set("jsj小王","小王");
        redisTemplate.opsForValue().set("sjk小李","小李");
        redisTemplate.opsForValue().set("jsj校花","校花");
        redisTemplate.opsForValue().set("jsj小草","小草");
        redisTemplate.opsForValue().set("sjk小刘","小刘");
        Set<String> keys = redisTemplate.keys("jsj*");
        keys.forEach(System.out::println);

    }

    //通过众多的key,获取对应的value
    @Test
    public void getMoreValuesByKeys(){
        //需求：一次性获取redis缓存中多个key的value
        //潜在隐患：循环key，获取value，可能会造成连接池的连接数增多，连接的创建和摧毁，消耗性能
        //解决方法：根据项目中的缓存数据结构的实际情况，数据结构为string类型的，使用RedisTemplate的multiGet方法；数据结构为hash，使用Pipeline(管道)，组合命令，批量操作redis

        //1. 先获取Keys
        redisTemplate.opsForValue().set("jsj小王","小王");
        redisTemplate.opsForValue().set("sjk小李","小李");
        redisTemplate.opsForValue().set("jsj校花","校花");
        redisTemplate.opsForValue().set("jsj小草","小草");
        redisTemplate.opsForValue().set("sjk小刘","小刘");
        Set<String> keys = redisTemplate.keys("jsj*");

        List<String> strings = redisTemplate.opsForValue().multiGet(keys);
        strings.forEach(System.out::println);
    }



}