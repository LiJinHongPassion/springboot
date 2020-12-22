package com.example.codeant.springbootsyncdemo.async;

import com.example.codeant.springbootsyncdemo.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.12.22
 */
@Service
public class SyncService {

    @Resource
    private JobService jobService;

    /**
     * @param name
     */
    public String testSync(String name) {
        //1. 异步调用方法
        Optional<Future<User>> optional = Optional.ofNullable(jobService.hasReturnValue(name));

        //2. 处理业务
        {
            /*********************************************************/

            //自旋锁 -- 自旋等待两秒后，来检测是否执行完成
            int i = 0;
            int sizeTime = 2;
            while (++i <= sizeTime) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    //2.1 异步函数执行完成
                    if (optional.isPresent() && optional.get().isDone()) {
                        return optional.get().get().toString();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    optional.get().cancel(false);
                    return "出错";
                }
            }
            //2.2 异步函数未执行完成
            return "自旋" + sizeTime + "s后，仍未获取到值";
        }

    }
}
