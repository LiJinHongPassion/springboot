#FROM java:9
# 基础镜像，没有任何其他的依赖
FROM scratch

MAINTAINER lijinhong<18315107186@189.cn>

# 参考https://blog.csdn.net/cuiyaoqiang/article/details/100193220
# 映射宿主机的JDK

# 设置JAVA环境变量
# 这里需要注意下，解压后有个目录的，为jre1.8.0_181,一开始没注意，启动时报了：exec: "java": executable file not found in $PATH: unknown 后才发现。
#RUN "touch /usr/jdk1.8"
ENV JAVA_HOME="/"
ENV PATH="${PATH}:${JAVA_HOME}/bin:${JAVA_HOME}/sbin"

EXPOSE 8081

ADD ./target/yuezhi-api-manager-1.0-SNAPSHOT.jar ./yuezhi-api-manager-1.0-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","yuezhi-api-manager-1.0-SNAPSHOT.jar"]




