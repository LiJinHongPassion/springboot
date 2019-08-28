---
title: Java-实现springbootdemo部署到docker
tags:
  - java
  - docker
  - springboot
categories:
  - java
declare: true
abstract: 摘要
message: 通行证
comments: true
reward: true
abbrlink: daba
date: 2019-05-21 13:22:07
password:
---

------

![](https://images.unsplash.com/photo-1542558817211-17b8e8699cc9?ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80)

<!-- more -->

<center>

​	<span id="m_name"></span>

​	<audio id="m_url_id" controls> <source src=""> <source src="horse.ogg" type="audio/ogg"> Your browser does not support this audio format. 

​	</audio> 

</center>  

------

# 简述

常见的单体应用部署是在liunx上配置好tomcat+java+mysql+redis，再将代码放在tomcat跑起来，导致花费的时间较多。现采用容器虚拟化技术docker进行部署，在liunx上安装docker，在开发时将代码和环境制作成docker镜像，在liunx只需拉取镜像，并实例化为容器，项目就跑起来了，不需要配置繁杂的环境。
**本文主要讲一下idea上怎样将springboot项目部署到docker。(本套操作没有实现将镜像上传至docker hub)**

开发环境：windows10+idea
部署环境：centos7.5+docker
项目github：https://github.com/LiJinHongPassion/springboot

# 1、安装docker

可根据官方的教程进行安装：<https://docs.docker.com/install/linux/docker-ce/centos/>

```
yum安装gcc相关
yum -y install gcc
yum -y install gcc-c++

卸载旧版本
yum -y remove docker docker-common docker-selinux docker-engine

安装需要的软件包
yum install -y yum-utils device-mapper-persistent-data lvm2

设置stable镜像仓库(国内阿里云镜像仓库)
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

更新yum软件包索引
yum makecache fast

安装DOCKER CE
yum -y install docker-ce

启动docker
systemctl start docker

测试
docker version
docker run hello-world

配置镜像加速
mkdir -p /etc/docker
vim  /etc/docker/daemon.json

#网易云
{"registry-mirrors": ["http://hub-mirror.c.163.com"] }
 
 #阿里云
{
  "registry-mirrors": ["https://｛自已的编码｝.mirror.aliyuncs.com"]
}

systemctl daemon-reload
systemctl restart docker
```

# 2、Docker开启远程API端口
`Docker`开启远程端口的目的是可以通过`Docker`提供的` remoteApi`文档 管理`Docker`并且可以操作`Docker`下容器，监控容器的各项指标，也可以通过`remoteApi`去实现自己监控`Docker`告警系统等。默认`Docker`并没有启动`remoteApi`，需要我们修改配置才能生效。

默认Centos7.X下配置文件地址在` /usr/lib/systemd/system/` 下面，修改 `/usr/lib/systemd/system/docker.service` 文件,命令：`sudo vim /usr/lib/systemd/system/docker.service`

在 `ExecStart=/usr/bin/dockerd` 配置文件后面加上` -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock` 保存并退出。

注 :  端口 2375 就是`docker remoteApi`的 端口，确保此端口linux没有被占用。
**防火墙开放端口，或者关闭防火墙`systemlctl firewalld stop`**

执行 重启 docker 命令  docker重新读取配置文件，并重新启动docker服务 命令 :  `sudo systemctl daemon-reload && systemctl restart docker`

# 3、配置idea上的docker
**1. 安装docker插件**
file->settings->plugins
![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/daba/1.png)
![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/daba/4.png)

**2. 编写springboot_docker_demo例子**
创建一个简单的springboot项目
![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/daba/2.png)
主要看一下pom.xml文件和DockerFile

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.5.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example.li</groupId>
    <artifactId>springboot_docker_demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot_docker_demo</name>
   <!-- 打包方式 -->
    <packaging>jar</packaging>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <docker.image.prefix>springboot</docker.image.prefix>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>

            <!-- docker插件 -->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>1.0.0</version>
                <configuration>
                    <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
                    <dockerDirectory></dockerDirectory>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

```
#指定基础镜像，在其上进行定制
FROM java:8

#维护者信息
#MAINTAINER wuweixiang <wuweixiang.alex@gmail.com>

#这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层
VOLUME /tmp

#复制上下文目录下的target/demo-0.0.1-SNAPSHOT.jar 到容器里
COPY /target/springboot_docker_demo-0.0.1-SNAPSHOT.jar springboot_docker_demo-0.0.1-SNAPSHOT.jar

#bash方式执行，使demo-0.0.1-SNAPSHOT.jar可访问
#RUN新建立一层，在其上执行这些命令，执行结束后， commit 这一层的修改，构成新的镜像。
RUN bash -c "touch /springboot_docker_demo-0.0.1-SNAPSHOT.jar"

#ADD /target/springboot_docker_demo-0.0.1-SNAPSHOT.jar springboot_docker_demo-0.0.1-SNAPSHOT.jar

#声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE 8080

#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["java","-jar","springboot_docker_demo-0.0.1-SNAPSHOT.jar"]
```

**3. 配置例子中的docker**

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/daba/3.png)

**4. 部署到docker**

在centos上启动docker

```
service docker start
```

查看centos的ip

```
ip addr
```

打包项目，会在根目录下生成项目jar包
![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/daba/5.png)

执行DockerFile脚本，生成镜像并根据该镜像生成相应的容器
![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/daba/6.png)
![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/daba/7.png)

访问ip:port/hello，这里是`http://192.168.26.130:8888/hello`
![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/daba/9.png)