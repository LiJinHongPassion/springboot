
docker run -p 8081:8081 -e "JAVA_OPTS=-server -XX:+PrintGC -XX:+PrintGCDetails -Xmx1g -Xms512m -Xss256k springboot_docker_demo-0.0.1-SNAPSHOT.jar" demo/performance-tomcat