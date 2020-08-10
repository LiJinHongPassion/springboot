package com.example.miaoshademo.lock.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLockUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 释放分布式锁 - 解锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

        Object result = redisTemplate.execute(
                redisScript,
                Collections.singletonList(lockKey),
                Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            System.out.println("解锁成功");
            return true;
        }

        System.out.println("解锁失败");
        return false;
    }


    /**
     * 尝试重复获取分布式锁 - 加锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId, long expireTime, int times, long interval) {
        try {
            for (int i = 0; i < times; i++) {
                System.out.println("获取锁第" + i + "次");
                //SET命令返回OK ，则证明获取锁成功
                Boolean ret = redisTemplate.opsForValue().setIfAbsent(
                        Collections.singletonList(lockKey),
                        Collections.singletonList(requestId),
                        expireTime,
                        TimeUnit.MILLISECONDS);
                if (ret) {
                    System.out.println("加锁成功");
                    return true;
                }
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("加锁失败");
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
