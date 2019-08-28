package cn.codesheep.springbt_security_jwt.service;

import cn.codesheep.springbt_security_jwt.database.UserRepository;
import cn.codesheep.springbt_security_jwt.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
