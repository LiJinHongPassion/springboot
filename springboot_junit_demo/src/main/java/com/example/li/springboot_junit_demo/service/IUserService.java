package com.example.li.springboot_junit_demo.service;


import java.util.List;
import java.util.Map;

public interface IUserService {

    public List<Map<String, Object>> login(String user_id, String user_password);

    public Map<String, Object> register(String user_id, String user_password);

    public Map<String, Object> updateUser(Map<String, Object> properties);

    public Map<String, Object> getUserById(String user_id);

}
