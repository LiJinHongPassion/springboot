package cn.codesheep.springbt_security_jwt.service;

import cn.codesheep.springbt_security_jwt.model.entity.User;

/**
 * @author LJH
 * @date 2019/8/23-10:35
 * @QQ 1755497577
 */
public interface AuthService {

    User register( User userToAdd );
    String login( String username, String password );
}
