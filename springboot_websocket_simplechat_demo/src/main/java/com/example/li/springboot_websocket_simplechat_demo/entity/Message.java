package com.example.li.springboot_websocket_simplechat_demo.entity;

/**
 * @author Li
 * @date 2019/5/17-13:18
 */

public class Message {

    private String inner;

    private String name;


    public String getInner() {
        return inner;
    }

    public void setInner(String inner) {
        this.inner = inner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Message() {
    }

    public Message(String name, String inner) {
        this.inner = inner;
        this.name = name;
    }

}