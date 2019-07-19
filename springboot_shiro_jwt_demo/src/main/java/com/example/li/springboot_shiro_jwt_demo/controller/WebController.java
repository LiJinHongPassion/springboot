package com.example.li.springboot_shiro_jwt_demo.controller;


import com.example.li.springboot_shiro_jwt_demo.bean.ResponseBean;
import com.example.li.springboot_shiro_jwt_demo.database.UserBean;
import com.example.li.springboot_shiro_jwt_demo.database.UserService;
import com.example.li.springboot_shiro_jwt_demo.exception.UnauthorizedException;
import com.example.li.springboot_shiro_jwt_demo.util.JWTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    private static final Logger LOGGER = LogManager.getLogger(WebController.class);

    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseBean login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        UserBean userBean = userService.getUser(username);
        if (userBean.getPassword().equals(password)) {
            return new ResponseBean(200, "Login success", JWTUtil.sign(username, password));
        } else {
            throw new UnauthorizedException();
        }
    }

    /**
     * 描述: 游客和登录用户访问不同资源
     *
     * @author LJH-1755497577 2019/7/19 13:15
     * @param
     * @return com.example.li.springboot_shiro_jwt_demo.bean.ResponseBean
     */
    @GetMapping("/article")
    public ResponseBean article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResponseBean(200, "You are already logged in", null);
        } else {
            return new ResponseBean(200, "You are guest", null);
        }
    }

    /**
     * 描述: @RequiresAuthentication 需要认证登陆后才能访问
     *
     * @author LJH-1755497577 2019/7/19 12:58
     * @param
     * @return com.example.li.springboot_shiro_jwt_demo.bean.ResponseBean
     */
    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResponseBean requireAuth() {
        return new ResponseBean(200, "You are authenticated", null);
    }

    /**
     * 描述: @RequiresRoles("admin") 需要admin的角色信息才能访问
     *
     * @author LJH-1755497577 2019/7/19 12:59
     * @param
     * @return com.example.li.springboot_shiro_jwt_demo.bean.ResponseBean
     */
    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResponseBean requireRole() {
        return new ResponseBean(200, "You are visiting require_role", null);
    }

    /**
     * 描述: @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"}) 需要同时有view和edit权限才能访问
     *
     * @author LJH-1755497577 2019/7/19 12:59
     * @param
     * @return com.example.li.springboot_shiro_jwt_demo.bean.ResponseBean
     */
    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResponseBean requirePermission() {
        return new ResponseBean(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return new ResponseBean(401, "Unauthorized", null);
    }
}
