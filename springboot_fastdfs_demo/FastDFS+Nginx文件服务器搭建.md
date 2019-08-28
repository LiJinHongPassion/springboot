##注意

阿里云服务器不能使用公网ip，只能使用内网ip：https://blog.csdn.net/u010448530/article/details/83995347 

storage配置、client测试、配置nginx访问中的ip设置为内网ip

通过nginx代理8888端口实现访问dfs

阿里云安全组需要开放相关8888端口

## 准备

| 名称                 | 说明                          | 下载地址|
| -------------------- | ----------------------------- | ----------------------------- |
| centos               | 7.6                           |[centos链接](http://isoredirect.centos.org/centos/7/isos/x86_64/CentOS-7-x86_64-DVD-1810.iso)|
| libfatscommon        | FastDFS分离出的一些公用函数包 |git clone https://github.com/happyfish100/libfastcommon.git --depth 1|
| FastDFS              | FastDFS本体                   |git clone https://github.com/happyfish100/fastdfs.git --depth 1|
| fastdfs-nginx-module | FastDFS和nginx的关联模块      |git clone https://github.com/happyfish100/fastdfs-nginx-module.git --depth 1|
| nginx                | nginx1.15.4                   |wget http://nginx.org/download/nginx-1.15.4.tar.gz|

## 磁盘目录

| 说明                                   | 位置               |
| -------------------------------------- | ------------------ |
| 所有安装包                             | /opt/fastdfs       |
| 安装位置                               | /usr/local/fastdfs |
| 数据存储位置                           | /home/dfs          |
| #这里我为了方便把日志什么的都放到了dfs |                    |

## 编译环境

```shell
yum install git gcc gcc-c++ make automake autoconf libtool pcre pcre-devel zlib zlib-devel openssl-devel wget vim -y
```

[yum源更换为阿里云yum源的教程](https://lijinhongpassion.github.io/codeant/b7d4.html)，解决下载速度慢

## 安装

#### 安装libfatscommon

```shell
# 1.创建进入fastdfs文件夹
	mkdir /usr/local/fastdfs #安装目录
	mkdir /home/dfs #数据存储位置
    mkdir /opt/fastdfs	#安装包存放
    cd /opt/fastdfs/
# 2.下载
    git clone https://github.com/happyfish100/libfastcommon.git --depth 1
# 3.拷贝
	cp -r libfastcommon/ /usr/local/fastdfs/
# 4.编译
    cd /usr/local/fastdfs/libfastcommon/
    ./make.sh && ./make.sh install #编译安装
```

#### 安装FastDFS

```shell
# 1.进入安装包存放目录
	cd /opt/fastdfs
# 2.下载
	git clone https://github.com/happyfish100/fastdfs.git --depth 1
# 3.拷贝
	cp -r fastdfs/ /usr/local/fastdfs/
# 4.编译
    cd /usr/local/fastdfs/fastdfs/
    ./make.sh && ./make.sh install #编译安装
# 5.配置文件准备
    cp /etc/fdfs/tracker.conf.sample /etc/fdfs/tracker.conf
    cp /etc/fdfs/storage.conf.sample /etc/fdfs/storage.conf
    cp /etc/fdfs/client.conf.sample /etc/fdfs/client.conf #客户端文件，测试用
    cp /usr/local/fastdfs/fastdfs/conf/http.conf /etc/fdfs/ #供nginx访问使用
    cp /usr/local/fastdfs/fastdfs/conf/mime.types /etc/fdfs/ #供nginx访问使用
```

#### 安装fastdfs-nginx-module

```shell
# 1.进入安装包存放目录
	cd /opt/fastdfs 
# 2.下载
	git clone https://github.com/happyfish100/fastdfs-nginx-module.git --depth 1
# 3.拷贝
	cp -r fastdfs-nginx-module/ /usr/local/fastdfs/
# 4.复制配置文件
	cp /usr/local/fastdfs/fastdfs-nginx-module/src/mod_fastdfs.conf /etc/fdfs
```

#### 安装nginx

```shell
# 1.进入安装包存放目录
	cd /opt/fastdfs 
# 2.下载
	wget http://nginx.org/download/nginx-1.15.4.tar.gz #下载nginx压缩包
# 3.解压
	tar -zxvf nginx-1.15.4.tar.gz -C /usr/local/fastdfs/ #解压
# 4.进入解压目录
	cd /usr/local/fastdfs/nginx-1.15.4/
# 5.添加fastdfs-nginx-module模块
	./configure --add-module=/usr/local/fastdfs/fastdfs-nginx-module/src/ 
# 6.编译
	make && make install #编译安装
```

## 单机部署

#### tracker配置

```shell
# 服务器ip为 192.168.26.101
# 修改tracker.conf
vim /etc/fdfs/tracker.conf
# 需要修改的内容如下
port=22122  # tracker服务器端口（默认22122,一般不修改）
base_path=/home/dfs  # 存储日志和数据的根目录
```

#### storage配置

```shell
vim /etc/fdfs/storage.conf
# 需要修改的内容如下
port=23000  # storage服务端口（默认23000,一般不修改）
base_path=/home/dfs  # 数据和日志文件存储根目录
store_path0=/home/dfs  # 第一个存储目录
tracker_server=192.168.26.101:22122  # tracker服务器IP和端口
http.server_port=8888  # http访问文件的端口(默认8888,看情况修改,和nginx中保持一致)
```

#### client测试

```shell
vim /etc/fdfs/client.conf
# 需要修改的内容如下
base_path=/home/dfs
tracker_server=192.168.26.101:22122    # tracker服务器IP和端口

# 请先启动tracker、storage服务，参照启动标题
systemctl stop firewalld.service && /etc/init.d/fdfs_trackerd start && /etc/init.d/fdfs_storaged start 
# 保存后测试,返回ID表示成功 如：group1/M00/00/00/xx.tar.gz
fdfs_upload_file /etc/fdfs/client.conf /opt/fastdfs/nginx-1.15.4.tar.gz
```

#### 配置nginx访问

```shell
vim /etc/fdfs/mod_fastdfs.conf
#需要修改的内容如下
tracker_server=192.168.26.101:22122  #tracker服务器IP和端口
url_have_group_name=true
store_path0=/home/dfs
#配置nginx.config
vim /usr/local/nginx/conf/nginx.conf
#添加如下配置
server {
    listen       8888;    ## 该端口为storage.conf中的http.server_port相同
    server_name  localhost;
    location ~/group[0-9]/ {
        ngx_fastdfs_module;
    }
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
    root   html;
    }
}

# 请先启动tracker、storage、nginx服务，关闭防火墙，参照启动标题

#测试下载，用外部浏览器访问刚才已传过的nginx安装包,引用返回的ID
http://192.168.26.101:8888/group1/M00/00/00/wKgAQ1pysxmAaqhAAA76tz-dVgg.tar.gz
#弹出下载单机部署全部跑通
```

## 分布式部署

#### tracker配置

```shell
# 服务器ip为 192.168.26.101
# 修改tracker.conf
vim /etc/fdfs/tracker.conf
# 需要修改的内容如下
port=22122  # tracker服务器端口（默认22122,一般不修改）
base_path=/home/dfs  # 存储日志和数据的根目录
```

#### storage配置

```shell
vim /etc/fdfs/storage.conf
# 需要修改的内容如下
port=23000  # storage服务端口（默认23000,一般不修改）
base_path=/home/dfs  # 数据和日志文件存储根目录
store_path0=/home/dfs  # 第一个存储目录
tracker_server=192.168.26.101:22122  # 服务器1	(tracker服务器IP和端口)
tracker_server=192.168.26.102:22122  # 服务器2
tracker_server=192.168.26.103:22122  # 服务器3
http.server_port=8888  # http访问文件的端口(默认8888,看情况修改,和nginx中保持一致)
vim /etc/fdfs/storage.conf
```

#### client测试

```shell
vim /etc/fdfs/client.conf
# 需要修改的内容如下
base_path=/home/moe/dfs
tracker_server=192.168.26.101:22122  # 服务器1	(tracker服务器IP和端口)
tracker_server=192.168.26.102:22122  # 服务器2
tracker_server=192.168.26.103:22122  # 服务器3

# 请先启动tracker、storage服务，参照启动标题

# 保存后测试,返回ID表示成功 如：group1/M00/00/00/xx.tar.gz
fdfs_upload_file /etc/fdfs/client.conf /usr/local/src/nginx-1.15.4.tar.gz
```

#### 配置nginx访问

```shell
vim /etc/fdfs/mod_fastdfs.conf
# 需要修改的内容如下
tracker_server=192.168.52.2:22122  # 服务器1
tracker_server=192.168.52.3:22122  # 服务器2
tracker_server=192.168.52.4:22122  # 服务器3
url_have_group_name=true
store_path0=/home/dfs
# 配置nginx.config
vim /usr/local/nginx/conf/nginx.conf
# 添加如下配置
server {
    listen       8888;    ## 该端口为storage.conf中的http.server_port相同
    server_name  localhost;
    location ~/group[0-9]/ {
        ngx_fastdfs_module;
    }
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
    root   html;
    }
}
```

## 启动

#### 防火墙

```shell
# 不关闭防火墙的话无法使用
systemctl stop firewalld.service # 关闭
systemctl restart firewalld.service # 重启
```

#### tracker

```shell
/etc/init.d/fdfs_trackerd start # 启动tracker服务
/etc/init.d/fdfs_trackerd restart # 重启动tracker服务
/etc/init.d/fdfs_trackerd stop # 停止tracker服务
chkconfig fdfs_trackerd on # 自启动tracker服务
```

#### storage

```shell
/etc/init.d/fdfs_storaged start # 启动storage服务
/etc/init.d/fdfs_storaged restart # 重动storage服务
/etc/init.d/fdfs_storaged stop # 停止动storage服务
chkconfig fdfs_storaged on # 自启动storage服务
```

#### nginx

```shell
/usr/local/nginx/sbin/nginx # 启动nginx
/usr/local/nginx/sbin/nginx -s reload # 重启nginx
/usr/local/nginx/sbin/nginx -s stop # 停止nginx
```

#### 检测集群

```shell
/usr/bin/fdfs_monitor /etc/fdfs/storage.conf
# 会显示会有几台服务器 有3台就会 显示 Storage 1-Storage 3的详细信息
```

## 说明

#### 配置文件

```shell
tracker_server # 有几台服务器写几个
group_name # 地址的名称的命名
bind_addr # 服务器ip绑定
store_path_count # store_path(数字)有几个写几个
store_path(数字) # 设置几个储存地址写几个 从0开始
```

#### 可能遇到的问题

```shell
如果不是root 用户 你必须在除了cd的命令之外 全部加sudo
如果不是root 用户 编译和安装分开进行 先编译再安装
如果上传成功 但是nginx报错404 先检查mod_fastdfs.conf文件中的store_path0是否一致
如果nginx无法访问 先检查防火墙 和 mod_fastdfs.conf文件tracker_server是否一致
如果不是在/usr/local/src文件夹下安装 可能会编译出错
```

## 卸载

参考：http://www.leftso.com/blog/244

```shell
# 1.停止服务
	service fdfs_trackerd stop
	service fdfs_storaged stop
# 2.通过storage.conf找到base_path和store_path然后删除	
	cat /etc/fdfs/storage.conf |grep base_path
	cat /etc/fdfs/storage.conf |grep store_path
	# 删除	## 注意:如果有未备份的文件，请先备份再删除
	rm -rf 查询出来的base_path路径
	rm -rf 查询出来的store_path路径
# 3.通过tracker.conf找到base_path然后删除
	cat /etc/fdfs/tracker.conf |grep base_path
	# 删除
	rm –rf 查询出来的base_path路径
# 4.删除配置文件目录
	rm -rf /etc/fdfs/
# 5.删除链接文件
	# 删除tracker的链接文件
    rm –rf /usr/local/bin/fdfs_trackerd
    rm –rf /usr/local/bin/stop.sh
    rm –rf /usr/local/bin/restart.sh
    # 删除storage的链接文件
    rm –rf /usr/local/bin/fdfs_storaged
# 6.删除/usr/bin目录下FastDFS的可执行文件
	rm -rf  /usr/bin/fdfs_*
# 7.删除/usr/include/目录下FastDFS相关的shell脚本
	rm -rf /usr/include/fastdfs/
# 8.删除/usr/lib64目录下的库文件
	rm -rf libfdfsclient*
# 9.删除/usr/lib/目录下的库
	rm -rf libfdfsclient*
```

