转载：https://www.cnblogs.com/nightOfStreet/p/11543768.html

先上主菜。多数据源配置步骤
引入依赖 tkmapper + druid + mysql + spring-boot-configuration-processor

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <version>2.1.5.RELEASE</version>
</dependency>
<!-- mysql and mybatis etc -->
<dependency>
    <groupId>tk.mybatis</groupId>
    <artifactId>mapper-spring-boot-starter</artifactId>
    <version>2.1.4</version>
</dependency>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.11</version>
</dependency>

<!-- druid -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.10</version>
</dependency>
```

配置如下
启动类加上@EnableConfigurationProperties。
OceanApplication

```java
/**
 * EnableConfigurationProperties 使 @ConfigurationProperties 注解的类（需要加@Component）生效。
   */
   @EnableConfigurationProperties
   @SpringBootApplication
   public class OceanApplication {
       public static void main(String[] args) {
           SpringApplication.run(OceanApplication.class, args);
       }
   }
```

yml配置相应属性
application.yml 属性自定义名称,只需要和对应java类对应即可

```yml
druid:
  # 数据源配置
  user: root
  password: xxxx
  driverClass: com.mysql.cj.jdbc.Driver
  # 初始化 最小 最大
  initialSize: 5
  minIdle: 5
  maxActive: 20
  testOnBorrow: false
  urlIceberg: jdbc:mysql://xxx.xxx.xxx.xxx/iceberg?serverTimezone=GMT%2B8&characterEncoding=UTF-8&useSSL=false  # 设置时区
  urlOcean: jdbc:mysql://xxx.xxx.xxx.xxx/ocean?serverTimezone=GMT%2B8&characterEncoding=UTF-8&useSSL=false  # 设置时区
  urlAccount: jdbc:mysql://xxx.xxx.xxx.xxx/account?serverTimezone=GMT%2B8&characterEncoding=UTF-8&useSSL=false  # 设置时区　
```
自定义属性加载父类BaseProperty
映射yml配置 。 

```java
/**

 * datasource base class

 * @author qucheng
   */
   @Data
   @ConfigurationProperties(prefix = "druid")
   @Component
   class BaseProperty {

   protected String user;

   protected String password;

   protected String driverClass;

   protected int initialSize;

   protected int maxActive;

   protected int minIdle;

   protected boolean testOnBorrow;

   protected String urlAccount;

   protected String urlOcean;

   protected String urlIceberg;


    DataSource createDataSource(String url) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        return dataSource;
    }

}
```

各数据源配置自己的映射路径（mapper接口+xml文件）
配置路径 -- 此处省略2个其他配置

```java
/**
 * account datasource
 * MapperScan（如果要使用表名映射实体使用tk）注解用于绑定扫描的包和指定的数据源,且指定目录下的mapper无需加注解处理
 *
 */
   @Component
   @MapperScan(basePackages = "com.codeant.ocean.mapper.account", sqlSessionFactoryRef = "accountSqlSessionFactory")
   public class AccountConfig extends BaseProperty {

   @Bean(name = "accountDataSource")
   public DataSource createDataSource() {
       return createDataSource(urlAccount);
   }

   @Bean(name = "accountTransactionManager")
   public DataSourceTransactionManager accountTransactionManager() {
       return new DataSourceTransactionManager(createDataSource());
   }

   @Bean(name = "accountSqlSessionFactory")
   public SqlSessionFactory masterSqlSessionFactory(@Qualifier("accountDataSource") DataSource accountDataSource)
           throws Exception {
       final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
       sessionFactory.setDataSource(accountDataSource);
       String mapperLocation = "classpath:mapper/account/*.xml";
       sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
               .getResources(mapperLocation));
       return sessionFactory.getObject();
   }
   }
```

以上过程也出现的几个问题

多数据源共用了一个yml基础配置，除了数据源url地址不一致之外。
自定义yml属性配置项需要
引入spring-boot-configuration-processor。
启动类使用注解@EnableConfigurationProperties
解析类上面要引入3个注解@Data只是为了方便。@ConfigurationProperties(prefix = "druid")指明读取的是"druid"开始的属性。@Component表示由容器管理
配置类@MapperScan注解是使用的tk.mybatis.spring.annotation.MapperScan注解。org.mybatis.spring.annotation.MapperScan也可以用但是它不能配合使用@Table注解映射表。