---
title: Java-springboot整合security+jwt权限系统设计

---

------

![](https://images.unsplash.com/photo-1563394927360-08299fbdadb3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80)

<!-- more -->

<center>

​	<span id="m_name"></span>

​	<audio id="m_url_id" controls> <source src=""> <source src="horse.ogg" type="audio/ogg"> Your browser does not support this audio format. 

​	</audio> 

</center>  

------

## 简述


- **关于 Spring Security**
Web系统的认证和权限模块也算是一个系统的基础设施了，几乎任何的互联网服务都会涉及到这方面的要求。在Java EE领域，成熟的安全框架解决方案一般有 Apache Shiro、Spring Security等两种技术选型。Apache Shiro简单易用也算是一大优势，但其功能还是远不如 Spring Security强大。Spring Security可以为 Spring 应用提供声明式的安全访问控制，起通过提供一系列可以在 Spring应用上下文中可配置的Bean，并利用 Spring IoC和 AOP等功能特性来为应用系统提供声明式的安全访问控制功能，减少了诸多重复工作。

- **关于JWT**
JSON Web Token (JWT)，是在网络应用间传递信息的一种基于 JSON的开放标准（(RFC 7519)，用于作为JSON对象在不同系统之间进行安全地信息传输。主要使用场景一般是用来在 身份提供者和服务提供者间传递被认证的用户身份信息。关于JWT的科普，可以看看阮一峰老师的《JSON Web Token 入门教程》。

本文则结合 Spring Security和 JWT两大利器来打造一个简易的权限系统。

demo地址：https://github.com/LiJinHongPassion/springboot/tree/master/springbt_security_jwt

---

## 文件目录

springbt_security_jwt
    │  SpringbtSecurityJwtApplication.java	启动类
    │  
    ├─comm
    │      Const.java	常量类
    │      
    ├─config
    │      WebSecurityConfig.java	Spring Security配置类
    │      
    ├─controller
    │      JwtAuthController.java	
    │      TestController.java
    │      
    ├─database
    │      UserRepository.java	模拟数据源
    │      
    ├─filter
    │      JwtTokenFilter.java	jwt过滤器
    │      
    ├─model
    │  └─entity
    │          Role.java	角色类
    │          User.java	用户类
    │          
    ├─service
    │  │  AuthService.java	权限服务
    │  │  UserService.java
    │  │  
    │  └─impl
    │          AuthServiceImpl.java	权限服务接口
    │          
    └─util
            JwtTokenUtil.java	jwt工具类（生成jwt和验证jwt）

---

## 入门教程

#### 实体类

**role.java**

```java
/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
public class Role {

    private Long id;

    private String name;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
```

**user.java**

此处所创建的 User类继承了 Spring Security的 UserDetails接口，从而成为了一个符合 Security安全的用户，即通过继承 UserDetails，即可实现 Security中相关的安全功能。

```java
/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
public class User implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User(Long id, String username, String password, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 下面为实现UserDetails而需要的重写方法！

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add( new SimpleGrantedAuthority( role.getName() ) );
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
```

---

#### 依赖

pom.xml

```xml
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.0</version>
        </dependency>
```

---

#### jwt相关类

**Const.java**

常量类

```java
/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
public class Const {

    public static final long EXPIRATION_TIME = 432_000_000;     // 5天(以毫秒ms计)
    public static final String SECRET = "CodeAntSecret";      // JWT密码
    public static final String TOKEN_PREFIX = "Bearer";         // Token前缀
    public static final String HEADER_STRING = "Authorization"; // 存放Token的Header Key
}
```

**JwtTokenUtil,java**

主要用于对 JWT Token进行各项操作，比如生成Token、验证Token、刷新Token等

```java
/**
 * @author LJH jwt工具类
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -5625635588908941275L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey( Const.SECRET )
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + Const.EXPIRATION_TIME * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, Const.SECRET )
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        );
    }
}
```

**JwtTokenFilter.java**

创建Token过滤器，用于每次外部对接口请求时的Token处理

```java
/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal ( HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String authHeader = request.getHeader( Const.HEADER_STRING );
        if (authHeader != null && authHeader.startsWith( Const.TOKEN_PREFIX )) {
            final String authToken = authHeader.substring( Const.TOKEN_PREFIX.length() );
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                	if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
            }
        }
        chain.doFilter(request, response);
    }
}
```

---

#### service业务编写

**AuthService.java**

```java
/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
public interface AuthService {

    User register( User userToAdd );
    String login( String username, String password );
}
```

**AuthServiceImpl.java**

```java
/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    // 登录
    @Override
    public String login( String username, String password ) {

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken( username, password );

        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //通过用户名获取用户
        final UserDetails userDetails = userDetailsService.loadUserByUsername( username );
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    // 注册
    @Override
    public User register( User userToAdd ) {

        final String username = userToAdd.getUsername();
        if( userRepository.findByUsername(username)!=null ) {
            return null;
        }

        //加密密码，与WebSecurityConfig加密方式对应
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword( encoder.encode(rawPassword) );

        return userRepository.save(userToAdd);
    }
}
```

**UserService.java**

需要实现UserDetailsService接口，实现安全验证

```java
/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    //根据用户名加载用户信息
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }
}
```

---

#### 配置类

**WebSecurityConfig.java**

这是一个高度综合的配置类，主要是通过重写 `WebSecurityConfigurerAdapter` 的部分 `configure`配置，来实现用户自定义的部分。

```java
/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    //jwt过滤器
    @Bean
    public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //认证service的加密方式
    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( userService ).passwordEncoder( new BCryptPasswordEncoder() );
    }

    @Override
    protected void configure( HttpSecurity httpSecurity ) throws Exception {
        //关闭打开的csrf（跨域）保护
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/authentication/**").permitAll()
                .antMatchers(HttpMethod.POST).authenticated()
                .antMatchers(HttpMethod.PUT).authenticated()
                .antMatchers(HttpMethod.DELETE).authenticated()
                .antMatchers(HttpMethod.GET).authenticated();

        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers().cacheControl();
    }
}
```

#### 测试

###### 数据源

**UserRepository.java**

用户名：codeAnt，密码：codeAnt123，权限：ROLE_NORMAL

```java
/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
@Component
public class UserRepository {

    private static List<User> users = new ArrayList<>();
    private static List<Role> roles = new ArrayList<>();

   static {
        roles.add(new Role(Long.valueOf("1"), "ROLE_NORMAL"));
        //roles.add(new Role(Long.valueOf("2"), "ROLE_ADMIN"));
        users.add(new User(Long.valueOf("11621"), "codeAnt", "$2a$10$Gw.Cf/uL3o21c6jpSHmdY..pi.K9P0KHummmVadYbnMcB1woMa5t.", roles));
    }

    public User findByUsername(String s) {
        for(User user : users){
            if(user.getUsername().equals(s)){
                return user;
            }
        }
        return null;
    }

    public User save(User userToAdd) {
        if(findByUsername(userToAdd.getUsername()) == null){
            users.add(userToAdd);
            return userToAdd;
        }
        return null;
    }
}
```

###### controller

登录和注册的 Controller：

```
/**
 * @author LJH
 * 20190312
 */
@RestController
public class JwtAuthController {

    @Autowired
    private AuthService authService;

    // 登录
    @RequestMapping(value = "/authentication/login", method = RequestMethod.POST)
    public String createToken( String username,String password ) throws AuthenticationException {
        return authService.login( username, password );
    }

    // 注册
    @RequestMapping(value = "/authentication/register", method = RequestMethod.POST)
    public User register( @RequestBody User addedUser ) throws AuthenticationException {
        return authService.register(addedUser);
    }
}
```

再编写一个测试权限的 Controller：

```
/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
@RestController
public class TestController {

    // 测试普通权限
    @PreAuthorize("hasAuthority('ROLE_NORMAL')")
    @RequestMapping( value="/normal/test", method = RequestMethod.GET )
    public String test1() {
        return "ROLE_NORMAL /normal/test接口调用成功！";
    }

    // 测试管理员权限
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping( value = "/admin/test", method = RequestMethod.GET )
    public String test2() {
        return "ROLE_ADMIN /admin/test接口调用成功！";
    }
}
```

这里给出两个测试接口用于测试权限相关问题，其中接口 `/normal/test`需要用户具备普通角色（`ROLE_NORMAL`）即可访问，而接口`/admin/test`则需要用户具备管理员角色（`ROLE_ADMIN`）才可以访问。

## 启动验证

**数据源用户信息：**

用户名：codeAnt，密码：codeAnt123，权限：ROLE_NORMAL



**验证流程：**

- 登录：登录获取token
- 未携带token访问普通权限
- 携带token普通权限
- 携带token管理员权限

---

#### 验证

**登录**

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle4/c1.png)

**未携带token访问普通权限**

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle4/c3.png)

**携带token普通权限**

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle4/c2.png)

**携带token管理员权限**

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle4/c4.png)

