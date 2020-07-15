## springboot整合redis, 实现CRUD

#### 依赖

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.1.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

---

#### 配置连接器

springboot对两种连接器都提供了支持

- **lettuce**

- #### **Jedis**

> **Lettuce 和 Jedis的区别**
>
> Lettuce 和 Jedis 都是Redis的client，所以他们都可以连接 Redis Server。
> Jedis在实现上是直接连接的Redis Server，如果在多线程环境下是非线程安全的。
> 每个线程都去拿自己的 Jedis 实例，当连接数量增多时，资源消耗阶梯式增大，连接成本就较高了。
>
> Lettuce的连接是基于Netty的，Netty 是一个多线程、事件驱动的 I/O 框架。连接实例可以在多个线程间共享，当多线程使用同一连接实例时，是线程安全的。
> 所以，一个多线程的应用可以使用同一个连接实例，而不用担心并发线程的数量。
> 当然这个也是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。
>
> 通过异步的方式可以让我们更好的利用系统资源，而不用浪费线程等待网络或磁盘I/O。
> 所以 Lettuce 可以帮助我们充分利用异步的优势。
>
> 使用连接池，为每个Jedis实例增加物理连接Lettuce的连接是基于Netty的，连接实例（StatefulRedisConnection）可以在多个线程间并发访问，应为StatefulRedisConnection是线程安全的，所以一个连接实例（StatefulRedisConnection）就可以满足多线程环境下的并发访问，当然这个也是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。

---

#### 默认配置

> 使用springboot中的默认配置, 例如对象序列化方式都使用默认的

```yaml
spring:
  redis:
    host: xx.xx.xx.xx
    port: 6379
    password:
    # 连接超时时间（记得添加单位，Duration）
    timeout: 10000ms
    # Redis默认情况下有16个分片，这里配置具体使用的分片
    database: 1
    
    # 选用lettuce, 配置lettuce连接器
    lettuce:
      pool:
        # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-active: 8
        # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-wait: -1ms
        # 连接池中的最大空闲连接 默认 8
        max-idle: 8
        # 连接池中的最小空闲连接 默认 0
        min-idle: 0
        
    # jedis, 配置jedis连接器
    jedis:
      pool:
        # 连接池最大连接数（使用负值表示没有限制） 默认 8
        max-active: 8
        # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
        max-wait: -1ms
        # 连接池中的最大空闲连接 默认 8
        max-idle: 8
        # 连接池中的最小空闲连接 默认 0
        min-idle: 0
```

---

##### 自定义配置 - Lettuce 

> 在前面默认配置的基础上, 自定义配置连接器的一些属性 ( [序列化器](https://docs.spring.io/spring-data/redis/docs/2.3.1.RELEASE/reference/html/#redis:serializer) | [哈希映射](https://docs.spring.io/spring-data/redis/docs/2.3.1.RELEASE/reference/html/#redis.hashmappers.root) | [读写分离](https://docs.spring.io/spring-data/redis/docs/2.3.1.RELEASE/reference/html/#redis:write-to-master-read-from-replica) | [哨兵模式](https://docs.spring.io/spring-data/redis/docs/2.3.1.RELEASE/reference/html/#redis:sentinel) )

```java
@Configuration
class AppConfig {
	/**
     * Lettuce - 基础配置( 配置redis的IP\密码\端口\数据库片 )
     */	
  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
	RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration();
    //serverConfig.setDatabase("数据库片");
    //serverConfig.setHostName("IP地址");
    //serverConfig.setPassword("redis密码");
    //serverConfig.setPort(redis端口);
    return new LettuceConnectionFactory(serverConfig);
  }
}
```

```java
@Configuration
class WriteToMasterReadFromReplicaConfiguration {
	/**
     * Lettuce - 读写分离
     */
  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {

    LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
      .readFrom(SLAVE_PREFERRED)
      .build();
    RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration();
    //serverConfig.setDatabase("数据库片");
    //serverConfig.setHostName("IP地址");
    //serverConfig.setPassword("redis密码");
    //serverConfig.setPort(redis端口);
    return new LettuceConnectionFactory(serverConfig, clientConfig);
  }
}
```

```java
@Configuration
class AppConfig {
    /**
     * Lettuce - 哨兵模式
     */
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
      RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
      .master("mymaster")
      .sentinel("127.0.0.1", 26379)
      .sentinel("127.0.0.1", 26380);
        //sentinelConfig.setDatabase("数据库片");
        //sentinelConfig.setHostName("IP地址");
        //sentinelConfig.setPassword("redis密码");
        //sentinelConfig.setPort(redis端口);
      return new LettuceConnectionFactory(sentinelConfig);
    }
}
```

---

##### 自定义配置 - Jedis

>在前面默认配置的基础上, 自定义配置连接器的一些属性 ( [序列化器](https://docs.spring.io/spring-data/redis/docs/2.3.1.RELEASE/reference/html/#redis:serializer) | [哈希映射](https://docs.spring.io/spring-data/redis/docs/2.3.1.RELEASE/reference/html/#redis.hashmappers.root) | [哨兵模式](https://docs.spring.io/spring-data/redis/docs/2.3.1.RELEASE/reference/html/#redis:sentinel)  )

```java
@Configuration
class AppConfig {
	/**
     * Jedis - 基础配置( 配置redis的IP\密码\端口\数据库片 )
     */
  @Bean
  public JedisConnectionFactory redisConnectionFactory() {
      
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("server", 6379);
    //serverConfig.setDatabase("数据库片");
    //serverConfig.setHostName("IP地址");
    //serverConfig.setPassword("redis密码");
    //serverConfig.setPort(redis端口);
      
    return new JedisConnectionFactory(config);
  }
}
```

```java
@Configuration
class AppConfig {
    /**
     * Jedis - 哨兵模式
     */
    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
      RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
      .master("mymaster")
      .sentinel("127.0.0.1", 26379)
      .sentinel("127.0.0.1", 26380);
        //sentinelConfig.setDatabase("数据库片");
        //sentinelConfig.setHostName("IP地址");
        //sentinelConfig.setPassword("redis密码");
        //sentinelConfig.setPort(redis端口);
      return new JedisConnectionFactory(sentinelConfig);
    }
}
```

---

#### CRUD( 利用redisTemplate )

---












参考文献: [csdn](https://blog.csdn.net/qq_36781505/article/details/86612988?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-10.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-10.nonecase)	[官方文档](https://docs.spring.io/spring-data/redis/docs/2.3.1.RELEASE/reference/html/#redis)  [redisTemplate API](https://www.jianshu.com/p/19e851a3edba)