package com.example.li.springboot_swagger_demo.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author LJH
 * @date 2019/7/15-13:50
 */
@ApiModel(value = "用户的DTO", description = "是个DTO对象，传送给前端")
public class UserDTO {

    private String name;
    private Integer age;

    @ApiModelProperty(value = "性别", name = "sex", required = true, allowableValues = "男,女")
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
