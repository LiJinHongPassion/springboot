---
## Java-springboot整合guava本地缓存
---

------

![](https://images.unsplash.com/photo-1562102010-a7c462e96db7?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1074&q=80)


------

## 简述

---

前面讲了springboot整合redis作为外部缓存，本文讲解springboot整合guava作为本地缓存，虽然guava已经被新版本的springboot弃用，但是guava还是值得学习的。后面有时间会搭建一个Caffeine缓存的demo

demo地址：https://github.com/LiJinHongPassion/springboot/springboot_cache_guava_demo

---

## 概念

---

#### 为什么要是用内部缓存

在系统中，有些数据量不大、不常变化，但是访问十分频繁，例如省、市、区数据。针对这种场景，可以将数据加载到应用的内存中，以提升系统的访问效率，减少无谓的数据库和网路的访问。

内部缓存的限制就是存放的数据总量不能超出内存容量，毕竟还是在 JVM 里的。



#### 最简单的内部缓存 - Map

如果只是需要将一些数据缓存起来，避免不必要的数据库查询，那么 Map 就可以满足。

对于字典型的数据，在项目启动的时候加载到 Map 中，程序就可以使用了，也很容易更新。

```java
// 配置存放的Map
Map<String, String> configs = new HashMap<String, String>();
// 初始化或者刷新配置的
Mappublic void reloadConfigs() {
    Map<String, String> m = loadConfigFromDB();
    configs = m;
}
// 使用
configs.getOrDefault("auth.id", "1");
```



#### 功能强大的内部缓存 - Guava Cache / Caffeine

如果你需要缓存有强大的性能，或者对缓存有更多的控制，可以使用 Guava 里的 Cache 组件。

它是 Guava 中的缓存工具包，是非常简单易用且功能强大的 JVM 内缓存，支持多种缓存过期策略。

**本地缓存的优点：**

- 直接使用内存，速度快，通常存取的性能可以达到每秒千万级
- 可以直接使用 Java 对象存取

**本地缓存的缺点：**

- 数据保存在当前实例中，无法共享
- 重启应用会丢失



#### Guava Cache 的替代者 Caffeine

Spring 5 使用 Caffeine 来代替 Guava Cache，应该是从性能的角度考虑的。从很多性能测试来看 Caffeine 各方面的性能都要比 Guava 要好。

Caffeine 的 API 的操作功能和 Guava 是基本保持一致的，并且 Caffeine 为了兼容之前 Guava 的用户，做了一个 Guava 的 Adapter， 也是十分的贴心。

如果想了解更多请参考：[是什么让 Spring 5 放弃了使用 Guava Cache](https://blog.csdn.net/qq_38398479/article/details/70578876)？

---

## 搭建

---

#### 依赖

```xml
<!--guava-start-->
<!-- https://mvnrepository.com/artifact/com.google.guava/guava -->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>27.1-jre</version>
</dependency>
<!--因为springboot新版本已经弃用guava作为本地缓存，所以需要切换到以前的spring版本-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-support</artifactId>
    <version>4.3.13.RELEASE</version>
</dependency>
<!--guava-end-->
```

------

#### 配置

**GuavaCacheConfig.java**

需要@EnableCaching开启缓存

```java
@Configuration
@EnableCaching
public class GuavaCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(
                CacheBuilder.newBuilder().
                        //缓存过期时间
                        expireAfterWrite(10, TimeUnit.SECONDS).
                        maximumSize(1000));
        return cacheManager;
    }
}
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

第一次访问http://localhost:8080/get?id=1后，10秒内再次访问的话，是访问的缓存，不是访问的模拟数据库