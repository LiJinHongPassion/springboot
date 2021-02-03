FROM java:8
# 基础镜像，没有任何其他的依赖
#FROM scratch
#FROM alpine
#FROM openjdk:9-jre-alpine

MAINTAINER lijinhong<18315107186@189.cn>

# 参考https://blog.csdn.net/cuiyaoqiang/article/details/100193220
# 映射宿主机的JDK

EXPOSE 8081

ADD ./target/yuezhi-api-manager-1.0-SNAPSHOT.jar ./yuezhi-api-manager-1.0-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","yuezhi-api-manager-1.0-SNAPSHOT.jar"]




