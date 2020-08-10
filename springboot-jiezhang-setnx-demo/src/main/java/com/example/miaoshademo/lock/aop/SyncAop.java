package com.example.miaoshademo.lock.aop;

import com.example.miaoshademo.lock.anno.SyncAnnotation;
import com.example.miaoshademo.lock.anno.SyncEnum;
import com.example.miaoshademo.lock.util.RedisLockUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Order(1)//执行顺序
@Aspect
@Component
public class SyncAop {


    @Autowired
    public RedisLockUtil lockUtil;

    @Around(value = "@annotation(sync)", argNames = "joinPoint,sync")
    public Object syncAround(ProceedingJoinPoint joinPoint, SyncAnnotation sync) throws Throwable {
        String requestId = UUID.randomUUID().toString();
        SyncEnum syncEnum = sync.value();
        try {
            //在这里阻塞，加锁
            boolean flag = lockUtil.tryGetDistributedLock(
                    syncEnum.getValue(),
                    requestId,
                    syncEnum.getExpireTime(),
                    syncEnum.getTimes(),
                    syncEnum.getInterval()
            );
            if (flag) {
                return joinPoint.proceed();
            } else {
                throw new Exception("获取分布式锁失败！有人正在结账");
            }
        } catch (Throwable t) {
            throw t;
        } finally {
            lockUtil.releaseDistributedLock(syncEnum.getValue(), requestId);
        }
    }

}