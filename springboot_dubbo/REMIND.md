#工程介绍
springboot_dubbo_0_interface_demo：MAVEN项目，存放实体类&接口
springboot_dubbo_1_userservice_provider_demo：Springboot项目，提供者，提供服务
springboot_dubbo_2_orderservice_consumer_demo：Springboot项目，消费者，消费服务

#部署过程
- 安装zookeeper（具体安装方式百度查找），**并运行**
- 安装springboot_dubbo_0_interface_demo
	打开springboot_dubbo_0_interface_demo项目，利用mvn的install命令，将项目安装在本地仓库
- 在zookeeper上注册提供者
	用idea打开springboot_dubbo_1_userservice_provider_demo，在application.yml根据情况中修改配置的dubbo信息，修改完成后执行
- 消费者订阅zookeeper上的提供者
	用idea打开springboot_dubbo_2_orderservice_consumer_demo，在application.yml根据情况中修改配置的dubbo信息，修改完成后执行

然后再浏览器中输入相应的url测试消费者是否能够成功调用提供者的方法

#参考
[Dubbo中文官网](http://dubbo.apache.org/zh-cn/)
[Dubbo注册中心参考手册](http://dubbo.apache.org/zh-cn/docs/user/references/registry/zookeeper.html)
[Dubbo配置方式讲解（注解，api，xml等方式）](http://dubbo.apache.org/zh-cn/docs/user/configuration/configuration-load-process.html)
[Dubbo配置参数参考手册](http://dubbo.apache.org/zh-cn/docs/user/references/xml/introduction.html)
[Dubbo推荐用法](http://dubbo.apache.org/zh-cn/docs/user/recommend.html)