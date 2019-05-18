package com.example.li.springboot_websocket_demo.entity;

/**
 * @author Li
 * @date 2019/5/17-13:20
 */

public class Greeting {

    private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
