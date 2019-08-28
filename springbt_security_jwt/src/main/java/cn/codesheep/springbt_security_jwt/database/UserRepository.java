package cn.codesheep.springbt_security_jwt.database;

import cn.codesheep.springbt_security_jwt.model.entity.Role;
import cn.codesheep.springbt_security_jwt.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

        //$2a$10$Gw.Cf/uL3o21c6jpSHmdY..pi.K9P0KHummmVadYbnMcB1woMa5t. 是codeAnt123加密后的密码
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
