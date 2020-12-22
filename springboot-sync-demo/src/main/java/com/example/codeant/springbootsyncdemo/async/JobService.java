package com.example.codeant.springbootsyncdemo.async;

import com.example.codeant.springbootsyncdemo.entity.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.12.22
 */
@Component
public class JobService {
    /**
     * 有返回值的异步
     * @return
     */
    @Async
    public Future<User> hasReturnValue(String name){
        System.out.println("Currently Executing thread name - " + Thread.currentThread().getName());
        try {
            User user = new User();
            user.setName(name);
            user.setSex("男");
            user.setAge("12");
            //随机睡眠0-5秒
            int i = (new Random().nextInt(5));
            System.out.println(Thread.currentThread().getName() + " --------------- 任务执行时间: " + i);
            TimeUnit.SECONDS.sleep(i);
            return new AsyncResult<>(user);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
}
