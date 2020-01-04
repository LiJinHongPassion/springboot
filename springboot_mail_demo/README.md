该项目是利用mail实现的邮件发送

demo：https://github.com/LiJinHongPassion/springboot/tree/master/springboot_mail_demo

三步走：
1. 申请smpt服务，例如163邮箱：https://jingyan.baidu.com/article/c275f6ba33a95de33d7567d9.html
2. 添加依赖
```xml
<!--mail start-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
        <!--mail end-->

```
3. 将授权信息填入application.properties
```properties
spring.mail.host=smtp.163.com
spring.mail.username=yang13260906172@163.com
spring.mail.password=yang13260906172
spring.mail.default-encoding=UTF-8

mail.fromMail.addr=yang13260906172@163.com

```
4. 创建邮件服务类（MailService&MailServiceImpl）



### 注意

在centos上会无法发送邮件，需要更改为以下配置：

```yaml
spring:
	mail:
    # 邮箱
    host: smtp.163.com
    username: yang13260906172@163.com
    password: yang13260906172
    default-encoding: UTF-8
    #    在centos上发送失败 -----------------------------------添加以下配置
    port: 465
    properties:
      mail:
        imap:
          ssl:
            socketFactory:
              fallback: false
        smtp:
          auth: true
          ssl:
            enable: true
            socketFactory:
              class: javax.net.ssl.SSLSocketFactory
          starttls:
            enable: true
            required: true
      test-connection: false

mail.fromMail.addr: yang13260906172@163.com
```

