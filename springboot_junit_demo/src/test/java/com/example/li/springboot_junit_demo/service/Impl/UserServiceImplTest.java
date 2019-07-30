package com.example.li.springboot_junit_demo.service.Impl; 

import com.example.li.springboot_junit_demo.SpringbootJunitDemoApplication;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/** 
* UserServiceImpl Tester. 
* 
* @author LJH 
* @since <pre>ÆßÔÂ 30, 2019</pre> 
* @version 1.0 
*/ 
@RunWith(SpringJUnit4ClassRunner.class)//??????¨¨??spring?????????¨¦????????????¡À????????¡ã????????????¡§¨¨¡ì?????¡À?¨¨??¨¨??¨¨????¡§???¨¨????¡§???
@SpringBootTest(classes = SpringbootJunitDemoApplication.class)//?????¡§?¡À?
public class UserServiceImplTest { 
    
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserServiceImpl service;

    @Before
    public void before() throws Exception { 
    
    } 
    
    @After
    public void after() throws Exception { 
    
    } 
    
    /** 
    * 
    * Method: login(String user_id, String user_password) 
    * 
    */ 
    @Test
    public void testLogin() throws Exception {
        List<Map<String, Object>> login = service.login("123", "123");
        logger.error("login£º" + login.toString());
    } 
    
    /** 
    * 
    * Method: register(String user_id, String user_password) 
    * 
    */ 
    @Test
    public void testRegister() throws Exception { 
    
    } 
    
    /** 
    * 
    * Method: updateUser(Map<String, Object> properties) 
    * 
    */ 
    @Test
    public void testUpdateUser() throws Exception { 
    
    } 
    
    /** 
    * 
    * Method: getUserById(String user_id) 
    * 
    */ 
    @Test
    public void testGetUserById() throws Exception {
        Map<String, Object> userById = service.getUserById("123");
        System.out.println("----------------------------------");
        logger.error("GetUserById£º" + userById.toString());
        System.out.println("----------------------------------");
    } 
    
        
    /** 
    * 
    * Method: changeStateOnline(String user_id) 
    * 
    */ 
    @Test
    public void testChangeStateOnline() throws Exception { 
    
        /* 
        try { 
           Method method = UserServiceImpl.getClass().getMethod("changeStateOnline", String.class); 
           method.setAccessible(true); 
           method.invoke(<Object>, <Parameters>); 
        } catch(NoSuchMethodException e) { 
        } catch(IllegalAccessException e) { 
        } catch(InvocationTargetException e) { 
        } 
        */ 
        } 

/** 
    * 
    * Method: changeStatedwonline(String user_id) 
    * 
    */ 
    @Test
    public void testChangeStatedwonline() throws Exception { 
    
        /* 
        try { 
           Method method = UserServiceImpl.getClass().getMethod("changeStatedwonline", String.class); 
           method.setAccessible(true); 
           method.invoke(<Object>, <Parameters>); 
        } catch(NoSuchMethodException e) { 
        } catch(IllegalAccessException e) { 
        } catch(InvocationTargetException e) { 
        } 
        */ 
        } 

} 
