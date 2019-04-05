package com.example.li.springboot_filter_interceptor_demo.entity;

import java.util.Date;

/**
 * @author Li
 * @date 2019/4/5-18:43
 */
public class BlackList {
    private int id;
    private String ip;
    private Date iptime; // 日期类型，格式：yyyy-MM-dd HH:mm:ss
    //构造器
    public BlackList() {
    }

    public BlackList(String ip, Date iptime) {
        this.ip = ip;
        this.iptime = iptime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getIptime() {
        return iptime;
    }

    public void setIptime(Date iptime) {
        this.iptime = iptime;
    }
}
