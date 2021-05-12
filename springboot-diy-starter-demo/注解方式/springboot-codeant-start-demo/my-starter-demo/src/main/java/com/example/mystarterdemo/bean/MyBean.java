package com.example.mystarterdemo.bean;

/**
 * @author lijinhong
 * @date 2021年05月12日 09:51
 * @Description:
 */
public class MyBean {
    private String beanName;
    private String packageName;

    public MyBean(String beanName, String packageName) {
        this.beanName = beanName;
        this.packageName = packageName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "beanName='" + beanName + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
