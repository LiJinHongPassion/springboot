package com.example.li.springboot_shiro_jwt_demo.shiro;


import com.example.li.springboot_shiro_jwt_demo.database.UserBean;
import com.example.li.springboot_shiro_jwt_demo.database.UserService;
import com.example.li.springboot_shiro_jwt_demo.util.JWTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);

    @Resource
    private UserService userService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        //1. 获取token
        String token = (String) auth.getCredentials();
        //2. 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        //3. 判断username是否存在token中
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        //4. 从数据库获取用户
        UserBean userBean = userService.getUser(username);
        //5. 判断用户是否存在数据库
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        //6. 校验token
        if (! JWTUtil.verify(token, username, userBean.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }


    /**
     * 鉴权
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     * 示例：controller中的注解 @RequiresRoles("admin")
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1. principals.toString() == token
        String username = JWTUtil.getUsername(principals.toString());
        //2. 从数据库获取对象
        UserBean user = userService.getUser(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //3. 添加角色
        simpleAuthorizationInfo.addRole(user.getRole());

        //4. 添加权限
        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);

        return simpleAuthorizationInfo;
    }
}
