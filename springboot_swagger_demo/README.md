---
title: Java-swagger-api框架（springboot整合）
---

------

![](https://images.unsplash.com/photo-1562101806-f2effc30ed54?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80)

<!-- more -->

<center>

​	<span id="m_name"></span>

​	<audio id="m_url_id" controls> <source src=""> <source src="horse.ogg" type="audio/ogg"> Your browser does not support this audio format. 

​	</audio> 

</center>  

------

# 1. 简述

swagger的作用

- 描述和测试API接口
- 自动生成API接口文档

# 2. 入门使用

github例子：https://github.com/LiJinHongPassion/springboot/tree/master/springboot_swagger_demo

## 2.1 添加依赖

```xml
		<!-- Swagger依赖-Start  -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
        <!-- Swagger依赖-End  -->

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.58</version>
        </dependency>
```

## 2.2 配置swagger

### 2.2.1 application.yml

```yaml
swagger:
  #项目标题
  title: SpringBoot学习
  #项目描述
  description: SpringBoot快速入门
  #版本
  version: 1.0.0
  #开发者名字
  name: codeAnt
  #开发者个人主页
  url: https://lijinhongpassion.github.io/
  #开发者邮箱
  email: 1755497577@qq.com
```



### 2.2.2 SwaggerConfig.java

```java
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author LJH
 * @date 2019/7/15-10:43
 */
@ConfigurationProperties(prefix = "swagger")//读取yml文件中的swagger配置，需要配置getset方法，或者使用lomlok
//@Data  //lomlok
@Configuration //必须存在
@EnableSwagger2 //必须存在
@EnableWebMvc //必须存在
//必须存在 扫描的API Controller包
@ComponentScan(basePackages = {"com.example.li.springboot_swagger_demo.controller"})
public class SwaggerConfig{

    private String title;
    private String description;
    private String version;

    private String name;
    private String url;
    private String email;

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(name, url, email);
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .contact(contact)
                .version(version)
                .build();
    }
    
    //set、get方法
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getVersion() {return version;}
    public void setVersion(String version) {this.version = version;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
```



### 2.2.3 WebMvcConfig.java

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LJH
 * @date 2019/7/15-10:35
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 添加swagger静态资源映射
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
```

### 2.2.4 springboot启动类

使用`@EnableWebMvc`注解开启MVC，不开启则配置的`WebMvcConfig.java`不会生效

```java
@SpringBootApplication
@EnableWebMvc//开启MVC
public class SpringbootSwaggerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSwaggerDemoApplication.class, args);
    }
}
```

## 2.3 测试

启动项目，访问接口文档地址http://localhost:8080/swagger/swagger-ui.html

这里8080后面的swagger是我项目访问路径

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/1a1f/1.png)

# 3. swagger常见注解

官网地址：https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X#quick-annotation-overview

常用注解： 

------

1、**@Api()**：用在请求的类上，表示对类的说明，也代表了这个类是swagger2的资源

参数：

```text
tags：说明该类的作用，参数是个数组，可以填多个。
value="该参数没什么意义，在UI界面上不显示，所以不用配置"
description = "用户基本信息操作"
```

2、**@ApiOperation()**：用于方法，表示一个http请求访问该方法的操作

参数：

```text
value="方法的用途和作用"    
notes="方法的注意事项和备注"    
tags：说明该方法的作用，参数是个数组，可以填多个。
格式：tags={"作用1","作用2"} 
（在这里建议不使用这个参数，会使界面看上去有点乱，前两个常用）
```

3、**@ApiModel()**：用于响应实体类上，用于说明实体作用

参数：

```text
value="实体类名"
description="描述实体的作用"  
```

4、**@ApiModelProperty**：用在属性上，描述实体类的属性

参数：

```text
value="用户名"  描述参数的意义
name="name"    参数的变量名
required=true     参数是否必选
allowableValues  =  "男,女"	 允许的值

```

5、**@ApiImplicitParams**：用在请求的方法上，包含多@ApiImplicitParam

6、**@ApiImplicitParam**：用于方法，表示单独的请求参数

参数：

```text
name="参数ming" 
value="参数说明" 
dataType="数据类型" 
paramType="query" 表示参数放在哪里
    · header 请求参数的获取：@RequestHeader
    · query   请求参数的获取：@RequestParam
    · path（用于restful接口） 请求参数的获取：@PathVariable
    · body（不常用）
    · form（不常用） 
defaultValue="参数的默认值"
required="true" 表示参数是否必须传

```

7、**@ApiParam()**：用于方法，参数，字段说明 表示对参数的要求和说明

参数：

```text
name="参数名称"
value="参数的简要说明"
defaultValue="参数默认值"
required="true" 表示属性是否必填，默认为false

```

8、**@ApiResponses**：用于请求的方法上，根据响应码表示不同响应

一个@ApiResponses包含多个@ApiResponse

9、**@ApiResponse**：用在请求的方法上，表示不同的响应

**参数**：

```text
code="404"    表示响应码(int型)，可自定义
message="状态码对应的响应信息"   

```

10、**@ApiIgnore()**：用于类或者方法上，不被显示在页面上

11、**@Profile({"dev", "test"})**：用于配置类上，表示只对开发和测试环境有用

------

**例子**

```java
/**
 * @author LJH
 * @date 2019/7/15-10:36
 */
@RestController
@Api(value = "用户模块")//板块描述
public class UserController {

    @GetMapping("/hello")
    @ApiOperation(value = "测试Swagger", notes = "hello方法")
    public String hello(){
        return "hello SpringBoot-swagger";
    }


    /**
     * 方法1：采用@ApiImplicitParams描述参数，当以实体类接收传递的参数时，需要添加@ApiIgnore()
     * @param id
     * @param user 传递的实体类
     * @return
     */

    @GetMapping("/test")
    @ApiOperation(value = "测试", notes = "测试swagger注解")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户名字", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "用户性别", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "age", value = "用户年龄", required = true, dataType = "int", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied",
                    responseHeaders = @ResponseHeader(name = "X-Rack-Cache", description = "Explains whether or not a cache was used", response = Boolean.class)),
            @ApiResponse(code = 404, message = "Pet not found") })
    public UserDTO test(@RequestParam(value = "id", required = false) String id,
                        @ApiIgnore() UserDTO user){

        user.setName("OK");
        return user;
    }

    /**
     * 方法1：采用@ApiParam描述参数，实体类的属性描述可以利用model的@ApiModelProperty、@ApiModel注解
     * @param id
     * @param user 传递的实体类
     * @return
     */

    @GetMapping("/test1")
    @ApiOperation(value = "测试1", notes = "测试swagger注解1")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied",
                    responseHeaders = @ResponseHeader(name = "X-Rack-Cache", description = "Explains whether or not a cache was used", response = Boolean.class)),
            @ApiResponse(code = 404, message = "Pet not found") })
    public UserDTO test1(@RequestParam(value = "id", required = false) @ApiParam(value = "用户的id", name = "用户的id", required = false) String id,
                        @ApiParam(value = "用户的Dto", name = "用户的对象", required = true) UserDTO user){

        user.setName("OK");
        return user;
    }
}

```

