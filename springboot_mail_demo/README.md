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