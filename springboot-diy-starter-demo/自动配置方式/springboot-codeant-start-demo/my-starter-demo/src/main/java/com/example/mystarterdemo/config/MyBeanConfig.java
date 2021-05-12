package com.example.mystarterdemo.config;

import com.example.mystarterdemo.bean.MyBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijinhong
 * @date 2021年05月12日 09:49
 * @Description:
 */
@Configuration
@ConfigurationProperties("my.bean")
public class MyBeanConfig {
    private String name;
    private String packageStr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    @Bean("MyBean")
    public MyBean createMyBean(){
        MyBean myBean = new MyBean(this.name, this.packageStr);
        System.out.println("++++++++++++++++++++++++");
        System.out.println("创建starter中的mybean: " + myBean.toString());
        System.out.println("++++++++++++++++++++++++");
        return myBean;
    }
}
