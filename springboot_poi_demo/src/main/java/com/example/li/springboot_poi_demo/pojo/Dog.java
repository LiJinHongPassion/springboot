package com.example.li.springboot_poi_demo.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：实体类
 * @author LJH
 * @date 2019/8/2-18:50
 * @QQ 1755497577
 */
public class Dog implements Serializable {

    @Excel(name = "狗昵称", orderNum = "0")
    private String dog_name;

    @Excel(name = "性别", orderNum = "1")
    private String dog_sex;

    @Excel(name = "年龄", orderNum = "2")
    private Integer dog_age;

    @Excel(name = "体重", orderNum = "3")
    private Float dog_height;

    @Excel(name = "种类", orderNum = "4")
    private String dog_kind;

    public String getDog_name() {
        return dog_name;
    }

    public void setDog_name(String dog_name) {
        this.dog_name = dog_name;
    }

    public String getDog_sex() {
        return dog_sex;
    }

    public void setDog_sex(String dog_sex) {
        this.dog_sex = dog_sex;
    }

    public Integer getDog_age() {
        return dog_age;
    }

    public void setDog_age(Integer dog_age) {
        this.dog_age = dog_age;
    }

    public Float getDog_height() {
        return dog_height;
    }

    public void setDog_height(Float dog_height) {
        this.dog_height = dog_height;
    }

    public String getDog_kind() {
        return dog_kind;
    }

    public void setDog_kind(String dog_kind) {
        this.dog_kind = dog_kind;
    }

    public Dog(String dog_name, String dog_sex, Integer dog_age, Float dog_height, String dog_kind) {
        this.dog_name = dog_name;
        this.dog_sex = dog_sex;
        this.dog_age = dog_age;
        this.dog_height = dog_height;
        this.dog_kind = dog_kind;
    }


    /**
     * 描述: 实体类对象转化为map
     *
     * @author LJH-1755497577 2019/8/2 19:53
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("狗昵称", getDog_name());
        map.put("性别", getDog_sex());
        map.put("年龄", getDog_age());
        map.put("体重", getDog_height());
        map.put("种类", getDog_kind());
        return map;
    }

    public Dog() {
    }
}
