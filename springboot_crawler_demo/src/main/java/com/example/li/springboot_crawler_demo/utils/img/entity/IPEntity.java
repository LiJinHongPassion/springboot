package com.example.li.springboot_crawler_demo.utils.img.entity;

/**
 * 描述: ip + 端口
 *
 * @author lijinhong
 * @date 19.12.2
 */
public class IPEntity {
    private String ip;
    private Integer port;

    public IPEntity(String ip, String port) {
        this.ip = ip;
        this.port = Integer.valueOf(port);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = Integer.valueOf(port);
    }

    @Override
    public String toString(){
        return ip + "|" + port + "\n";
    }
}
