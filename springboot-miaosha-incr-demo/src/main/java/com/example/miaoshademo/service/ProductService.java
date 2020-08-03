package com.example.miaoshademo.service;

import com.example.miaoshademo.dao.CodeantProductDao;
import com.example.miaoshademo.dao.CodeantProductLogDao;
import com.example.miaoshademo.entity.CodeantProduct;
import com.example.miaoshademo.entity.CodeantProductLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.7.31
 */
@Service
public class ProductService {

    @Autowired
    private CodeantProductDao productDao;
    @Autowired
    private CodeantProductLogDao productLogDao;


    /**
     * 查看id=1的商品
     * @return
     */
    public String get() {
        return productDao.selectByPrimaryKey(1).toString();
    }

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 将id=1的商品 的信息(id, num) 放至redis
     * @return
     */
    public String ready() {
        //获取商品信息
        CodeantProduct codeantProduct = productDao.selectByPrimaryKey(1);
        redisTemplate
                .opsForValue()
                .set(
                        String.valueOf(codeantProduct.getId()),
                        String.valueOf(codeantProduct.getNum())
                );
        System.out.println(redisTemplate.opsForValue().get(String.valueOf(codeantProduct.getId())));
        return "注意: 添加的数据放在redis的第10个库";
    }

    /**
     * 常规的秒杀会照成的问题
     * @param num
     * @param key
     * @return
     */
    @Transactional
    public String buy(Integer num, Integer key) {
        return productDao.update(num, key) == 1 ? "购买成功" : "购买失败";
    }

    /**
     *
     * @param num 购买数量
     * @param key 购买的商品ID
     * @return
     */
    @Transactional
    public String buy1(int num, int key) {

        //1. 查询redis上的库存是否充足, 高并发情况下 检查库存 与 减少库存 不是原子性，  以 increment > 0 为准      *
        Integer redis_num = Integer.valueOf(
                Objects.requireNonNull(
                        redisTemplate.opsForValue().get("1")
                )
        );
        if (redis_num < 1) return "库存不足!!";

        // 2.减少库存
        long value = redisTemplate.opsForValue().increment(String.valueOf(key), -(long) num);

        // 库存充足 可以异步操作,即使反馈购买成功的信息,并异步处理 扣减mysql数据库并生成订单
        if (value >= 0) {
            // update 数据库中商品库存和订单系统下单，单的状态未待支付
            // 分开两个系统处理时，可以用LCN做分布式事务，但是也是有概率会订单系统的网络超时
            // 也可以使用最终一致性的方式，更新库存成功后，发送mq，等待订单创建生成回调。
            boolean res = productDao.update(num, key) == 1;
            if (res) {
                //创建订单
                //createOrder(req);
                //记录购买日志
                productLogDao.insert(new CodeantProductLog( new Date(), key, num ));
            }

            return "成功购买";
        } else {
            //恢复扣减的redis库存
            redisTemplate.opsForValue().increment(String.valueOf(key), (long) num);
            return "redis库存不够";
        }
    }

}
