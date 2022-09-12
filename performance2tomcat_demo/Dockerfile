# https://juejin.cn/post/6844903983392243720
FROM openjdk:8u201-jre-alpine3.9

# 时区
ENV TZ=Asia/Shanghai

#这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层
VOLUME /tmp

#复制上下文目录下的target/demo-0.0.1-SNAPSHOT.jar 到容器里;maven中配置JAR_FILE
#COPY ${JAR_FILE} performance2tomcat_demo-0.0.1-SNAPSHOT.jar
COPY /target/performance2tomcat_demo-0.0.1-SNAPSHOT.jar app.jar

#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar ${0} ${@}"]