package com.example.li.springboot_mybatis_shiro_demo.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Li
 * @date 2019/4/3-22:14
 */
@Configuration
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager d = new DefaultWebSecurityManager();
        d.setCacheManager(cacheManager());
        d.setAuthenticator(authenticator());
        List<Realm> list = new ArrayList<>();
        list.add(mysqlRealm());
        list.add(secondRealm());
        d.setRealms(list);
//        d.setRealm(mysqlRealm());
        d.setRememberMeManager(rememberMeManager());
        d.setSessionManager(sessionManager());
        return d;
    }

    @Bean
    public EhCacheManager cacheManager(){
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    @Bean
    public ModularRealmAuthenticator authenticator(){
        ModularRealmAuthenticator m = new ModularRealmAuthenticator();
        m.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return m;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher h = new HashedCredentialsMatcher("MD5");
        h.setHashIterations(512);
        return h;
    }

    @Bean
    public MysqlRealm mysqlRealm(){
        MysqlRealm m = new MysqlRealm();
        m.setCredentialsMatcher(hashedCredentialsMatcher());
        return m;
    }

    @Bean
    public SecondRealm secondRealm(){
        SecondRealm m = new SecondRealm();
        m.setCredentialsMatcher(hashedCredentialsMatcher());
        return m;
    }

    /** * 注册DelegatingFilterProxy（Shiro） * * @param dispatcherServlet *  */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        // 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor a = new AuthorizationAttributeSourceAdvisor();
        a.setSecurityManager(securityManager());
        return a;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean s = new ShiroFilterFactoryBean();
        s.setSecurityManager(securityManager());
        s.setLoginUrl("/login.jsp");
        s.setSuccessUrl("/list.jsp");
        s.setUnauthorizedUrl("/unauthorized.jsp");

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/user/login", "anon");
        map.put("/user/register", "anon");
        map.put("/user.jsp", "authc,roles[user]");
        map.put("/admin.jsp", "authc,roles[admin]");
        map.put("/list.jsp", "user");
        map.put("/**", "authc");
        s.setFilterChainDefinitionMap(map);
        return s;
    }

    @Bean
    public RedisSessionDao redisSessionDao() {
        RedisSessionDao r = new RedisSessionDao();
        r.setActiveSessionsCacheName("shiro-activeSessionCache");
        r.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        return r;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager d = new DefaultWebSessionManager();
        d.setGlobalSessionTimeout(1800000);
        d.setDeleteInvalidSessions(true);
        d.setSessionValidationInterval(36000);
        d.setSessionValidationSchedulerEnabled(true);
        d.setSessionDAO(redisSessionDao());
        return d;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager c = new CookieRememberMeManager();
        c.setCookie(rememberMeCookie());
        return c;
    }

    @Bean
    public SimpleCookie sessionIdCookie(){
        SimpleCookie s = new SimpleCookie("sid");
        s.setHttpOnly(true);
        s.setMaxAge(-1);
        return s;
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie r = new SimpleCookie("rememberMe");
        r.setHttpOnly(true);
        r.setMaxAge(2592000);
        return r;
    }
}
