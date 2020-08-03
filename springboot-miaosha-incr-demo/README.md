# 利用Redis的原子操作 -- Incr实现秒杀

## 前提

> 如果你对redis的CRUD操作还不是很熟悉, 可以去阅读[springboot_neo4j_demo](https://github.com/LiJinHongPassion/springboot/tree/master/springboot-redis-demo)：springboot整合redis数据库利用redistemplate实现CRUD

> 该篇文章主要是对秒杀中redis的increment介绍, 关于其他秒杀相关可以查看参考文献, 他们已经描述得很全面了

---

## 准备

> **环境: **redis, mysql, jdk

---

## 问题

> 多个线程同时修改同一条数据 , MySQL和Redis怎么保证不会出现线程安全

**什么是原子性 ?** 

> > 参考: https://juejin.im/post/6844904080041574413	 诡异的并发之原子性
>
> **一个或者多个操作在 CPU 执行的过程中不被中断的特性称为原子性 。**
>
> **数据库事务中 **
>
> ```tex
> 原子性概念是这样子的：事务被当做一个不可分割的整体，包含在其中的操作要么全部执行，要么全部不执行。且事务在执行过程中如果发生错误，会被回滚到事务开始前的状态，就像这个事务没有执行一样。（也就是说：事务要么被执行，要么一个都没被执行）
> ```
>
> **多线程并发中**
>
> ```
> 原子性概念是这样子的：
> 
> 第一种理解：一个线程或进程在执行过程中，没有发生上下文切换。
> 上下文切换：指CPU从一个进程/线程切换到另外一个进程/线程(切换的前提就是获取CPU的使用权)。
> 
> 第二种理解：我们把一个线程中的一个或多个操作(不可分割的整体)，在CPU执行过程中不被中断的特性，称为原子性。(执行过程中，一旦发生中断，就会发生上下文切换)
> ```
>
> 
>
> **原子性是**  拒绝*多线程操作*的,不论是多核还是单核,具有原子性的量 ,  **同一时刻只能有一个线程来对它进行操作。**

---

## MySQL锁

> 1. 对于`UPDATE、DELETE、INSERT`语句，**InnoDB**会**自动**给涉及数据集加排他锁（X)
>
> 2. **InnoDB行锁和表锁都支持**！
>
>    1. 表锁
>       - 开销小，加锁快；不会出现死锁；锁定力度大，发生锁冲突概率高，并发度最低
>    2. 行锁
>       - 开销大，加锁慢；会出现死锁；锁定粒度小，发生锁冲突的概率低，并发度高
>
> 3. InnoDB只有通过**索引条件**检索数据**才使用行级锁**，否则，InnoDB将使用**表锁**
>
> 4. **表锁下又分为两种模式**：
>
>    - 表读锁（Table Read Lock）
>
>    - 表写锁（Table Write Lock）
>
>    - 从下图可以清晰看到，在表读锁和表写锁的环境下：
>
>      读读不阻塞，读写阻塞，写写阻塞
>
>      - 读读不阻塞：当前用户在读数据，其他的用户也在读数据，不会加锁
> - 读写阻塞：当前用户在读数据，其他的用户**不能修改当前用户读的数据**，会加锁！
>      - 写写阻塞：当前用户在修改数据，其他的用户**不能修改当前用户正在修改的数据**，会加锁！
> 
>  5. InnoDB实现了以下**两种**类型的行锁。
>
>     - 共享锁（S锁）：允许一个事务去读一行，阻止其他事务获得相同数据集的排他锁。
>       - 也叫做**读锁**：读锁是**共享**的，多个客户可以**同时读取同一个**资源，但**不允许其他客户修改**。
>     - 排他锁（X锁)：允许获得排他锁的事务更新数据，阻止其他事务取得相同数据集的共享读锁和排他写锁。
>       - 也叫做**写锁**：写锁是排他的，**写锁会阻塞其他的写锁和读锁**。

> **结论 : **MySQL对于`UPDATE、DELETE、INSERT`语句, **都会加锁,** **导致其他的SQL语句阻塞,** 在高并发的情况下, **虽然保证了数据安全** , 但是数据库会承受大量的IO操作, 大量的操作就会阻塞, 可能会导致宕机等, 所以需要缓冲这些服务, 可以使用MQ来异步操作

## Redis锁

> **参考 : **https://www.cnblogs.com/fengff/p/10913492.html

##### 命令`INCR`

> **Redis Incr 和 Redis Incrby命令是原子操作**

- Redis Incr 命令将 key 中储存的数字值增一，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
  - Redis Incrby 命令将 key 中储存的数字加上指定的增量值，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
  - 在rspringboot中直接是调用`redisTemplate.opsForValue().increment(String.valueOf(key), -(long) num)`就可以了

> 适用场景 : 计数器，高并发生成订单号，秒杀类的业务逻辑等。

**结合项目中的代码来讲: **

```java
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
```

`long value = redisTemplate.opsForValue().increment(String.valueOf(key), -(long) num);`在并发情况下是线程安全的 , 因为`increment`方法调用的是`Incrby `命令, `Incrby `是原子操作 ; 多线程的情况下 , 因为是原子操作 , 就只能有一个线程对Key进行修改 , 其他线程修改不了。

##### 锁`SETNX`

> 使用`!SETNX`加锁 , 官方文档 : http://www.redis.cn/commands/setnx.html

> 适用场景 : 分布式锁

##### 锁`SET`

	>官方不推荐用来做为锁
	>
	>官方文档 : http://www.redis.cn/commands/set.html

---

## 参考文献

> 阅读顺序
>
> 1. [如何设计秒杀](https://www.zhihu.com/question/54895548/answer/923987542)
> 2. [redis如何设计秒杀](https://blog.csdn.net/shendl/article/details/51092916?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase)
> 3. [redis如何方式超买超卖( 如何扣减库存 )](https://mp.weixin.qq.com/s/cdnRmiUFJTIYJVMfgJJw_Q)
> 4. [推荐使用redis原子操作+sql乐观锁实现](https://copyfuture.com/blogs-details/20200510085548659corx8bjvdg9td64)
> 5. [redis分布式锁](https://www.cnblogs.com/jiawen010/articles/11350125.html)