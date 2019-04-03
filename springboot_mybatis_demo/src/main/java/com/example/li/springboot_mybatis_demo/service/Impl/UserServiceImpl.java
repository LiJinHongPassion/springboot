package com.example.li.springboot_mybatis_demo.service.Impl;

import com.example.li.springboot_mybatis_demo.dao.base.Dao;
import com.example.li.springboot_mybatis_demo.dao.base.SearchDao;
import com.example.li.springboot_mybatis_demo.entity.User;
import com.example.li.springboot_mybatis_demo.entity.base.Entity;
import com.example.li.springboot_mybatis_demo.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource(name = "Dao")
    private Dao entityDao;
    @Resource(name = "SearchDao")
    private SearchDao searchDao;

    /***
     * 返回个人信息
     */
    public List<Map<String, Object>> login(String user_id, String user_password) {

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> result = new HashMap<>();

        user_id = user_id.trim();
        user_password = user_password.trim();
        if (user_id.isEmpty() || user_password.isEmpty()) {
            result.put("result", "-1");
            resultList.add(result);
            return resultList;
        }

        String condition = "user_id = '" + user_id + "' and user_password = '" + user_password + "'";

        //String joinEntity = "LEFT JOIN message on user.user_id = massage.receiver_id";

        resultList = searchDao.searchForeign(new String[]{"user.user_id",
                "user.user_name",
                "user.user_sex",
                "user.user_birthday",
                "user.user_address",
                "user.user_img_path",
                "user.user_integral",
                "user.user_label",
                "user.user_tel"},
                Entity.getTableName(User.class),
                null,//joinEntity,
                null,
                null,
                condition);

        if (resultList.size() == 0 || resultList == null) {
            result.put("result", "-1");
            resultList.add(result);
            return resultList;
        }
        changeStateOnline(user_id);
        result.put("result", "1");
        resultList.add(result);
        return resultList;

    }

    /**
     * 改变登录状态
     */
    private Boolean changeStateOnline(String user_id) {
        User user = entityDao.getByID(user_id, User.class);
        user.setUser_state("1");
        entityDao.updatePropByID(user, user_id);
        return true;
    }

    private Boolean changeStatedwonline(String user_id) {
        User user = entityDao.getByID(user_id, User.class);
        user.setUser_state("0");
        entityDao.updatePropByID(user, user_id);
        return true;
    }


    /**
     * 返回结果
     */
    @Override
    public Map<String, Object> register(String user_id, String user_password) {

        Map<String, Object> result = new HashMap<>();

        user_id = user_id.trim();
        user_password = user_password.trim();
        if (user_id.isEmpty() || user_password.isEmpty()) {
            result.put("result", "-1");
            return result;
        }

        List<User> temp_user = entityDao.getByCondition(" user_id = " + user_id, User.class);
        if (temp_user.size() != 0) {
            //用户已存在
            result.put("result", "0");
            return result;
        }

        long temp = System.currentTimeMillis();
        String user_name = Long.toHexString(temp);


        User user = new User();
        user.setUser_id(user_id);
        user.setUser_password(user_password);
        user.setUser_name(user_name);
        user.setUser_img_path("/pic/header/base/header_default_" + (int) (Math.random() * 5 + 1) + ".png");

        int reuslt_num = entityDao.save(user);
        if (reuslt_num == 1) {
            result.put("result", "1");
        } else {
            result.put("result", "-1");
        }
        return result;
    }


    /**
     * 返回结果
     */
    @Override
    public Map<String, Object> updateUser(Map<String, Object> properties) {
        Map<String, Object> result = new HashMap<>();
        User user = new User();

        user.setProperties(properties);
        int temp = entityDao.updatePropByID(user, user.getUser_id());
        if (temp == 1) {
            result.put("result", "1");
            return result;
        }
        result.put("result", "-1");
        return result;
    }


    @Override
    public Map<String, Object> getUserById(String user_id) {
        Map<String, Object> result = entityDao.getByID(user_id, User.class).toMap();
        if (result.isEmpty() || result == null) {
            result.put("result", "-1");
            return result;
        }
        result.put("result", "1");
        return result;
    }


}
