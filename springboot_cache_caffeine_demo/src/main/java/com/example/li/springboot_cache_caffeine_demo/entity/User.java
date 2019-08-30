package com.example.li.springboot_cache_caffeine_demo.entity;

import java.io.Serializable;

/**
 * <p>
 * 用户实体
 * </p>
 *
 * @package: com.example.li.springboot_cache_redis_demo.entity
 * @description: 用户实体
 * @author: LJH
 */
public class User implements Serializable {
    private static final long serialVersionUID = 2892248514883451461L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }
}
