# 1. 简述

jjwt干什么用的？令牌

举例：
- 服务器端：公司
- 客户端：员工

**申请令牌**：员工注册到公司，公司根据员工的个人特征给员工分发令牌，公司确认某人是否为该公司员工，令牌就是唯一凭借。
**验证令牌**：将令牌交给公司，公司进行验证，验证不通过就抛出异常，验证通过就返回员工属性（JWT 7个官方字段，详细请查看jjwt简介）。

demo地址：https://github.com/LiJinHongPassion/springboot/tree/master/springboot_jjwt_demo

# 2. 入门教程
## 2.1 添加依赖

jwt：https://jwt.io/ 指明方法1添加依赖
github：https://github.com/jwtk/jjwt 指明方法2添加依赖

```xml
		<!-- JJWT依赖-Start  -->
            <!--方法1-start-->
<!--            <dependency>-->
<!--                    <groupId>io.jsonwebtoken</groupId>-->
<!--                    <artifactId>jjwt</artifactId>-->
<!--                    <version>0.9.0</version>-->
<!--            </dependency>-->
            <!--方法1-end-->

            <!--方法2-start-->
            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt-api</artifactId>
                <version>0.10.7</version>
            </dependency>
            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt-impl</artifactId>
                <version>0.10.7</version>
                <scope>runtime</scope>
            </dependency>
            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt-jackson</artifactId>
                <version>0.10.7</version>
                <scope>runtime</scope>
            </dependency>
            <!-- 如果你想使用 (PS256, PS384, PS512) 算法，添加以下依赖:-->
            <dependency>
                <groupId>org.bouncycastle</groupId>
                <artifactId>bcprov-jdk15on</artifactId>
                <version>1.60</version>
                <scope>runtime</scope>
            </dependency>
            <!--方法2-end-->
        <!-- JJWT依赖-End  -->
```



## 2.2 过滤器
### 2.2.1 编写过滤器

对过滤到的请求验证token，但是对哪些请求过滤，需要我们在配置文件里面配置路径，在2.2.2中会演示配置

MyFilter.java

```java
import com.example.li.springboot_jjwt_demo.service.JwtService;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LJH
 * @date 2019/7/16-6:16
 * @QQ 1755497577
 */
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request =(HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("authorization"); //获取请求传来的token
        if( token == null){
            response.getWriter().write("请携带token");
            return;
        }
        Claims claims = JwtService.parsePersonJWT(token); //验证token
        if (claims == null) {  
            response.getWriter().write("请携带token");
        }else {
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
```

jwtService会报错，后面会编写这个类

### 2.2.2 注册过滤器

对2.2.1编写的过滤器进行注册，并添加需要过滤的路径` registration.addUrlPatterns("/user/hello");`

BeanRegisterConfig.java

```java
import com.example.li.springboot_jjwt_demo.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LJH
 * @date 2019/7/16-6:16
 * @QQ 1755497577
 */
@Configuration
public class BeanRegisterConfig {

    @Bean
    public FilterRegistrationBean createFilterBean() {
        //过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/user/hello"); //需要过滤的接口
        return registration;
    }
}
```



## 2.3 JWT辅组类

### 2.3.1 JwtUtils.java

jwt工具类包含两个部分：创建jwt和解析jwt

```java
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * 描述:
 * jwt基础工具类
 *
 * @author LJH
 * @date 2019/7/16-6:16
 * @QQ 1755497577
 */
public class JwtUtils {


    /**
     * jwt解密，需要密钥和token，如果解密失败，说明token无效
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken)
                    .getBody();
            return claims;
        } catch (JwtException ex) {
            return null;
        }
    }

    /**
     * 创建token
     *
     * @param map 主题，也差不多是个人的一些信息，为了好的移植，采用了map放个人信息，而没有采用JSON
     * @param audience 发送谁
     * @param issuer 个人签名
     * @param jwtId 相当于jwt的主键,不能重复
     * @param TTLMillis Token过期时间
     * @param base64Security 生成签名密钥
     * @return
     */
    public static String createJWT(Map map, String audience, String issuer, String jwtId, long TTLMillis, String base64Security) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        
        //生成签名密钥 就是一个base64加密后的字符串？
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        
        //签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setIssuedAt(now)
                .setSubject(map.toString())
                .setIssuer(issuer)
                .setId(jwtId)
                .setAudience(audience)
                .signWith(signingKey, signatureAlgorithm);  //设置签名使用的签名算法和签名使用的秘钥
        //添加Token过期时间
        if (TTLMillis >= 0) {
            // 过期时间
            long expMillis = nowMillis + TTLMillis;
            // 现在是什么时间
            Date exp = new Date(expMillis);
            // 系统时间之前的token都是不可以被承认的
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }
}
```

### 2.3.2 JwtService.java

该类是对JwtUtils.java的封装类，进行二次封装，提供创建（加密）和验证（解密）的方法

```java
import com.example.li.springboot_jjwt_demo.util.JwtUtils;
import io.jsonwebtoken.Claims;

import java.util.Map;

/**
 * 描述:
 * JWT服务类
 *
 * @author LJH
 * @date 2019/7/16-10:50
 * @QQ 1755497577
 */
public class JwtService {

    /**
     * token 过期时间, 单位: 秒. 这个值表示 30 天
     */
    private static final long TOKEN_EXPIRED_TIME = 30 * 24 * 60 * 60;
    /**
     * jwt 加密解密密钥
     */
    private static final String JWT_SECRET = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";
    /**
     * jwt 加密解密密钥
     */
    private static final String JWT_ISSUER = "LJH";

    /**
     * 描述:创建令牌
     * @author LJH-1755497577 2019/7/16 10:52
     * @param map 主题，也差不多是个人的一些信息，为了好的移植，采用了map放个人信息，而没有采用JSON
     * @param audience 发送谁
     * @return java.lang.String
     */
	public static String createPersonToken(Map map, String audience) {
        String personToken = JwtUtils.createJWT(map, audience, UUID.randomUUID().toString(), JWT_ISSUER, TOKEN_EXPIRED_TIME, JWT_SECRET);
        return personToken;
    }

    /**
     * 描述:解密JWT
     *
     * @author LJH-1755497577 2019/7/16 10:54
     * @param personToken JWT字符串,也就是token字符串
     * @return io.jsonwebtoken.Claims
     */
    public static Claims parsePersonJWT(String personToken) {
        Claims claims = JwtUtils.parseJWT(personToken, JWT_SECRET);
        return claims;
    }
}
```

## 2.4 测试

### 2.4.1 编写controller

```java
import com.example.li.springboot_jjwt_demo.service.JwtService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LJH
 * @date 2019/7/16-6:16
 * @QQ 1755497577
 */
@RestController
public class LoginController {

    //需要token验证才能访问
    @RequestMapping("user/hello")
    public String user(){
        return   "hello";
    }

    //获取token
    @RequestMapping("user/test")
    public String test(){

        Map<String, Object> map = new HashMap<>();
        map.put("name", "codeAnt");
        map.put("age", 21);

        return JwtService.createPersonToken(map, "codeAnt");
    }
}
```

**user/hello**：这个路径在2.2.2中配置有过滤器，所以访问的时候会过滤，判断是否携带token

### 2.4.2 接口测试

**获取token**：http://localhost:8080/user/test

![](./1.jpg)

**不携带token访问**：http://localhost:8080/user/hello
不携带就会报错，抛出错误提示信息

![](./2.jpg)

**携带token访问**：http://localhost:8080/user/hello

authorization：这个字段是在2.2.1编写过滤器中编写的

![](./3.jpg)