
#开启tomcat
java -jar ./springboot-miaosha-demo-0.0.1-SNAPSHOT.jar  > ./tomcat_my_hot_list.log 2>&1 &

# 测试常规的秒杀接口
ab -n 4000 -c  2000 -T application/x-www-form-urlencoded -p ./data http://127.0.0.1:9090/ms/buy

# 测试redis秒杀接口
ab -n 4000 -c  2000 -T application/x-www-form-urlencoded -p ./data http://127.0.0.1:9090/ms/buy1

