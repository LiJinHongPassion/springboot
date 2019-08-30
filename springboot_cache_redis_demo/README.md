---
## Java-springboot2.x整合redis缓存

------

![](https://images.unsplash.com/photo-1558981285-501cd9af9426?ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80)

<!-- more -->

<center>

​	<span id="m_name"></span>

​	<audio id="m_url_id" controls> <source src=""> <source src="horse.ogg" type="audio/ogg"> Your browser does not support this audio format. 

​	</audio> 

</center>  

------

## 简述

在如今高并发的互联网应用中，缓存的地位举足轻重，对提升程序性能帮助不小。而 3.x开始的 Spring也引入了对 Cache的支持，那对于如今发展得如火如荼的 Spring Boot来说自然也是支持缓存特性的。缓存的方式有本地缓存和远程缓存。但本文将讲述如何将reids缓存（远程缓存）应用到 Spring Boot应用中。在以后会讲解本地缓存的搭建。

demo地址：https://github.com/LiJinHongPassion/springboot/springboot_cache_redis_demo

---

## 文件结构

springboot_cache_redis_demo
                │  SpringbootCacheRedisDemoApplication.java	启动类
                │
                ├─config
                │      RedisConfig.java	redis配置文件
                │
                ├─controller
                │      UserController.java	
                │
                ├─entity
                │      User.java	实体类
                │
                └─service
                    │  UserService.java
                    │
                    └─impl
                            UserServiceImpl.java

---

## 入门搭建

---

#### 依赖

```xml
<!--redis-start-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- 对象池，使用redis时必须引入 -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
<!--redis-end-->
```

---

#### 配置

**RedisConfig.java**

需要@EnableCaching开启缓存

```java
/**
 * <p>
 * redis配置
 * </p>
 *
 * @package: com.example.li.springboot_cache_redis_demo.config
 * @description: redis配置
 * @author: LJH
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching//开启缓存
public class RedisConfig {

    /**
     * 默认情况下的模板只能支持RedisTemplate<String, String>，也就是只能存入字符串，因此支持序列化
     */
    @Bean
    public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
```

**application.yml**

```yaml
spring:
  redis:
    host: 47.103.20.12
    port: 6379
    password: 
    # 连接超时时间（记得添加单位，Duration）
    timeout: 10000ms
    # Redis默认情况下有16个分片，这里配置具体使用的分片
    database: 1
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
  cache:
    # 一般来说是不用配置的，Spring Cache 会根据依赖的包自行装配
    type: redis
logging:
  level:
    com.xkcoding: debug
```



---

#### 实体类

```java
/**
 * <p>
 * 用户实体
 * </p>
 *
 * @package: com.example.li.springboot_cache_redis_demo.entity
 * @description: 用户实体
 * @author: LJH
 */
public class User implements Serializable {
    private static final long serialVersionUID = 2892248514883451461L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }
}
```

---

#### 服务层

spring提供了下面四种缓存注解：

**@CachePut** 用于某个方法，每次被调用，此方法都执行，并把结果更新到PutCache配置的地方，一般用于缓存更新。

**@CacheEvict** 调用有此注解的方法可以删除一个缓存

**@CachesEvict** 调用有此注解的方法可以删除多个缓存，很多时候一个操作会涉及到多个缓存的更新或删除。

**@Cacheable** 用于某个方法，希望这个方法添加缓存，此方法被调用的时候，如果有缓存，此方法不执行。

```java
/**
 * <p>
 * UserService
 * </p>
 *
 * @package: com.example.li.springboot_cache_redis_demo.service.impl
 * @description: UserService
 * @author: LJH
 */
@Service
public class UserServiceImpl implements UserService {
    /**
     * 模拟数据库
     */
    private static final Map<Long, User> DATABASES = new ConcurrentHashMap();

    /**
     * 初始化数据
     */
    static {
        DATABASES.put(1L, new User(1L, "user1"));
        DATABASES.put(2L, new User(2L, "user2"));
        DATABASES.put(3L, new User(3L, "user3"));
    }

    /**
     * 保存或修改用户
     *
     * @param user 用户对象
     * @return 操作结果
     */
    @CachePut(value = "user", key = "#user.id")
    @Override
    public User saveOrUpdate(User user) {
        DATABASES.put(user.getId(), user);
        return user;
    }

    /**
     * 获取用户
     *
     * @param id key值
     * @return 返回结果
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public User get(Long id) {
        // 我们假设从数据库读取
        return DATABASES.get(id);
    }

    /**
     * 删除
     *
     * @param id key值
     */
    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Long id) {
        DATABASES.remove(id);
    }
}
```

---

#### 控制层

```java
/**
 * @author LJH
 * @date 2019/8/30-1:29
 * @QQ 1755497577
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("saveOrUpdate")
    public String saveOrUpdate(){
        userService.saveOrUpdate(new User(4L, "user4"));
        return "ok";
    }

    @GetMapping("get")
    public User get(Long id){
        return userService.get(id);
    }

    @GetMapping("delete")
    public String delete(Long id){
        userService.delete(id);
        return "d-ok";
    }
}
```

---

## 测试

每点击一个链接，观察redis对应库的数据变化

http://localhost:8080/saveOrUpdate
http://localhost:8080/get?id=1
http://localhost:8080/delete?id=1