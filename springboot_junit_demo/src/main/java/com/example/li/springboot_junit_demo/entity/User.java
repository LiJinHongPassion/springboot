package com.example.li.springboot_junit_demo.entity;


import com.example.li.springboot_junit_demo.entity.base.Entity;

public class User extends Entity {
    private String user_id;

    private String user_password;

    private String user_name;

    private String user_sex;

    private String user_birthday;

    private String user_address;

    private String user_type;

    private String user_img_path;

    private String user_integral;

    private String user_state;

    private String user_label;

    private String user_tel;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password == null ? null : user_password.trim();
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name == null ? null : user_name.trim();
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex == null ? null : user_sex.trim();
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday == null ? null : user_birthday.trim();
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address == null ? null : user_address.trim();
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type == null ? null : user_type.trim();
    }

    public String getUser_img_path() {
        return user_img_path;
    }

    public void setUser_img_path(String user_img_path) {
        this.user_img_path = user_img_path == null ? null : user_img_path.trim();
    }

    public String getUser_integral() {
        return user_integral;
    }

    public void setUser_integral(String user_integral) {
        this.user_integral = user_integral == null ? null : user_integral.trim();
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }

    public String getUser_label() {
        return user_label;
    }

    public void setUser_label(String user_label) {
        this.user_label = user_label == null ? null : user_label.trim();
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel == null ? null : user_tel.trim();
    }

    @Override
    public String getTableName() {
        // TODO Auto-generated method stub
        return "user";
    }

    @Override
    public String getPrimaryKey() {
        // TODO Auto-generated method stub
        return "user_id";
    }
}