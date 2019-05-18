package com.example.li.springboot_websocket_demo.entity;

/**
 * @author Li
 * @date 2019/5/17-13:18
 */

public class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}