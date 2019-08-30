---
title: Java-springboot整合caffeine本地缓存
tags: [java,springboot,caffeine,cache]
categories: [java]
declare: true
abstract: 摘要
message: 通行证
comments: true
reward: true
date: 2019-08-30 13:46:00
password:
---

------

![](https://images.unsplash.com/photo-1562184965-071835a7e37a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80)

<!-- more -->

<center>

​	<span id="m_name"></span>

​	<audio id="m_url_id" controls> <source src=""> <source src="horse.ogg" type="audio/ogg"> Your browser does not support this audio format. 

​	</audio> 

</center>  

------
## 简述
---
demo地址：https://github.com/LiJinHongPassion/springboot/tree/master/springboot_cache_caffeine_demo

---

## 搭建

------

#### 依赖

```xml
<!--caffeine-start-->
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
    <version>2.6.0</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
<!--caffeine-end-->
```

------

#### 配置

**CacheConfig.java**

需要@EnableCaching开启缓存

```java
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * 必须要指定这个Bean，refreshAfterWrite=5s这个配置属性才生效
     *
     * @return
     */
    @Bean
    public CacheLoader<Object, Object> cacheLoader() {
        CacheLoader<Object, Object> cacheLoader = new CacheLoader<Object, Object>() {

            @Override
            public Object load(Object key) throws Exception {
                return null;
            }

            // 重写这个方法将oldValue值返回回去，进而刷新缓存
            @Override
            public Object reload(Object key, Object oldValue) throws Exception {
                return oldValue;
            }
        };

        return cacheLoader;
    }
}
```

**application.yml**

```yaml
spring:
  cache:
    caffeine:
      spec: initialCapacity=50,maximumSize=500,expireAfterAccess=5s,expireAfterWrite=10s,refreshAfterWrite=5s


#  initialCapacity=[integer]: 初始的缓存空间大小
#  maximumSize=[long]: 缓存的最大条数
#  maximumWeight=[long]: 缓存的最大权重
#  expireAfterAccess=[duration]: 最后一次写入或访问后经过固定时间过期
#  expireAfterWrite=[duration]: 最后一次写入后经过固定时间过期
#  refreshAfterWrite=[duration]: 创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
#  weakKeys: 打开key的弱引用
#  weakValues：打开value的弱引用
#  softValues：打开value的软引用
#  recordStats：开发统计功能
```



------

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

------

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
        System.out.println("这是从数据库读取的，不是从缓存读取的");
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

------

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

------

## 测试

第一次访问http://localhost:8080/get?id=1后，5秒内再次访问的话，是访问的缓存，不是访问的模拟数据库