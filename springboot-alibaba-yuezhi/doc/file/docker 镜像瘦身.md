## Docker镜像瘦身

原创 禹过留声 [技术茶话会](javascript:void(0);) *1月10日*

收录于话题

\#docker 1

 

\#镜像 1

 

\#多阶段构建 1

 

\#开发运维 2

 

\#最佳实践 1

Docker 是一个用于开发，交付和运行应用程序的开发平台。它能够将应用程序和基础架构分开，保证开发，测试， 部署的环境完全一致，从而达到快速交付的目的。但是在实际项目中，会对项目中的模块或者服务进行细分， 导致部署的镜像过多（50+ 个），过大（打包压缩后的镜像达 50G+），这给部署带来了不小的隐患，特别是私有化部署（通过移动介质拷贝镜像进行部署）。本文从多篇镜像瘦身的文章入手，并进行实践验证，结合官方的Dockerfile最佳实践[1] 总结了镜像压缩的4种方法和日常实践的多个技巧。

## 镜像构建

### 构建方式

镜像构建的方式有两种，一种是通过 `docker build` 执行 Dockerfile 里的指令来构建镜像，另一种是通过 `docker commit` 将存在的容器打包成镜像。通常我们都是使用第一种方式来构建容器，二者的区别就像批处理和单步执行一样。

### 体积分析

Docker镜像是由很多镜像层（Layers）组成的（最多127层）， Dockerfile 中的每条指定都会创建镜像层，不过**只有 `RUN`, `COPY`, `ADD` 会使镜像的体积增加**。这个可以通过命令 `docker history image_id` 来查看每一层的大小。这里我们以官方的 alpine:3.12[2] 为例看看它的镜像层情况。

```
FROM scratch
ADD alpine-minirootfs-3.12.0-x86_64.tar.gz /
CMD ["/bin/sh"]
```

![图片](Desktop/codeant/git/springboot/springboot-alibaba-yuezhi/doc/file/img/640)对比 Dockerfile 和镜像历史层数发现 `ADD` 命令层占据了 5.57M 大小，而 `CMD` 命令层并不占空间。

镜像的层就像 `Git` 的每一次提交 `Commit`, 用于保存镜像的上一个版本和当前版本之间的差异。所以当我们使用 `docker pull` 命令从公有或私有的 Hub 上拉取镜像时，它只会下载我们尚未拥有的层。这是一种非常高效的共享镜像的方式，但是有时会被错误使用，比如反复提交。![图片](Desktop/codeant/git/springboot/springboot-alibaba-yuezhi/doc/file/img/640)从上图看出，基础镜像 alpine:3.12 占据了 5.57M 大小，idps_sm.tar.gz 文件占据了 4.52M。但是命令 `RUN rm -f ./idps_sm.tar.gz` 并没有降低镜像大小， 镜像大小由一个基础镜像和两次 `ADD` 文件构成。

### 瘦身方法

了解了镜像构建中体积增大的原因，那么就可以对症下药：**精简层数**或**精简每一层大小**。

•精简层数的方法有如下几种：

  •RUN指令合并  •多阶段构建

   •精简每一层的方法有如下几种：     • 使用合适的基础镜像（首选 alpine）     • 删除 RUN 的缓存文件

## 镜像瘦身

关于镜像瘦身这块的实际操作以打包 redis 镜像为例，在打包之前我们先拉取官方 redis 的镜像， 发现标签为6的镜像大小为 104M， 标签为 6-alpine 的镜像大小为 31.5M。打包的流程如下:

1.选择基础镜像，更新软件源，安装打包工具2.下载源码并进行打包安装3.清理不需要的安装文件

按照上述的流程，我们编写如下的Dockerfile[3]，该镜像使用命令 `docker build --no-cache -t optimize/redis:multiline -f redis_multiline .` 打包后镜像大小为 441M。

```
FROM ubuntu:focal

ENV REDIS_VERSION=6.0.5
ENV REDIS_URL=http://download.redis.io/releases/redis-$REDIS_VERSION.tar.gz

# update source and install tools
RUN sed -i "s/archive.ubuntu.com/mirrors.aliyun.com/g; s/security.ubuntu.com/mirrors.aliyun.com/g" /etc/apt/sources.list 
RUN apt update 
RUN apt install -y curl make gcc

# download source code and install redis
RUN curl -L $REDIS_URL | tar xzv
WORKDIR redis-$REDIS_VERSION
RUN make
RUN make install

# clean up
RUN rm  -rf /var/lib/apt/lists/* 

CMD ["redis-server"]
```

### RUN指令合并

指令合并是最简单也是最方便的降低镜像层数的方式。该操作节省空间的原理是在同一层中清理“缓存”和工具软件。还是打包 redis 的需要，指令合并的Dockerfile[4]如下，打包后的镜像大小为 292M。

```
FROM ubuntu:focal

ENV REDIS_VERSION=6.0.5
ENV REDIS_URL=http://download.redis.io/releases/redis-$REDIS_VERSION.tar.gz

# update source and install tools
RUN sed -i "s/archive.ubuntu.com/mirrors.aliyun.com/g; s/security.ubuntu.com/mirrors.aliyun.com/g" /etc/apt/sources.list &&\
    apt update &&\
    apt install -y curl make gcc &&\

# download source code and install redis
    curl -L $REDIS_URL | tar xzv &&\
    cd redis-$REDIS_VERSION &&\
    make &&\
    make install &&\

# clean up
    apt remove -y --auto-remove curl make gcc &&\
    apt clean &&\
    rm  -rf /var/lib/apt/lists/* 

CMD ["redis-server"]
```

使用 `docker history `分析 optimize/redis:multiline 和 optimize/redis:singleline 镜像，得到如下情况：![图片](Desktop/codeant/git/springboot/springboot-alibaba-yuezhi/doc/file/img/640)分析上图发现，镜像 optimize/redis:multiline 中清理数据的几层并没有降低镜像的大小，这就是上面说的共享镜像层带来的问题。所以指令合并的方法是通过在同一层中将缓存和不用的工具软件清理掉，以达到减小镜像体积的目的。

### 多阶段构建

多阶段构建方法是官方打包镜像的最佳实践，它是将精简层数做到极致的方法。通俗点讲它是将打包镜像分成两个阶段，一个阶段用于开发，打包，该阶段包含构建应用程序所需的所有内容；一个用于生产运行，该阶段只包含你的应用程序以及运行它所需的内容。这被称为“建造者模式”。两个阶段的关系有点像JDK和JRE的关系。使用多阶段构建肯定会降低镜像大小，但是瘦身的粒度和编程语言有关系，对编译型语言效果比较好，因为它去掉了编译环境中多余的依赖，直接使用编译后的二进制文件或jar包。而对于解释型语言效果就不那么明显了。

依然还是上面打包 redis 镜像的需求，使用多阶段构建的 Dockerfile[5]，打包后的进行大小为135M。

```
FROM ubuntu:focal AS build

ENV REDIS_VERSION=6.0.5
ENV REDIS_URL=http://download.redis.io/releases/redis-$REDIS_VERSION.tar.gz

# update source and install tools
RUN sed -i "s/archive.ubuntu.com/mirrors.aliyun.com/g; s/security.ubuntu.com/mirrors.aliyun.com/g" /etc/apt/sources.list &&\
    apt update &&\
    apt install -y curl make gcc &&\

# download source code and install redis
    curl -L $REDIS_URL | tar xzv &&\
    cd redis-$REDIS_VERSION &&\
    make &&\
    make install

FROM ubuntu:focal
# copy
ENV REDIS_VERSION=6.0.5
COPY --from=build /usr/local/bin/redis* /usr/local/bin/

CMD ["redis-server"]
```

相比 optimize/redis:singleline 改动有以下三点：

1.第一行多了As build, 为后面的COPY做准备2.第一阶段中没有了清理操作，因为第一阶段构建的镜像只有编译的目标文件（二进制文件或jar包）有用，其它的都无用3.第二阶段直接从第一阶段拷贝目标文件

同样的，使用 `docker history` 查看镜像体积情况：![图片](Desktop/codeant/git/springboot/springboot-alibaba-yuezhi/doc/file/img/640)

比较我们使用多阶段构建的镜像和官方提供 redis:6（无法和 redis:6-alpine 相比，因为 redis:6 和 ubuntu:focal 都是基于 debain 的镜像），发现二者有 30M 的空间。研究 redis:6 的 Dockerfile[6] 发现如下"骚操作":

```
serverMd5="$(md5sum /usr/local/bin/redis-server | cut -d' ' -f1)"; export serverMd5; \
find /usr/local/bin/redis* -maxdepth 0 \
        -type f -not -name redis-server \
        -exec sh -eux -c ' \
            md5="$(md5sum "$1" | cut -d" " -f1)"; \
            test "$md5" = "$serverMd5"; \
        ' -- '{}' ';' \
        -exec ln -svfT 'redis-server' '{}' ';' \
```

编译 redis 的源码发现二进制文件 redis-server 和 redis-check-aof(aof持久化), redis-check-rdb（rdb持久化）, redis-sentinel（redis哨兵）是相同的文件，大小为 11M。官方镜像通过上面的脚本将后三个通过 ln 来生成。

### 使用合适的基础镜像

基础镜像，推荐使用 Alpine。Alpine 是一个高度精简又包含了基本工具的轻量级 Linux 发行版，基础镜像只有 4.41M，各开发语言和框架都有基于 Alpine 制作的基础镜像，强烈推荐使用它。进阶可以尝试使用scratch和busybox镜像进行基础镜像的构建。从官方镜像 redis:6（104M） 和 redis:6-alpine（31.5M） 就可以看出 alpine 的镜像只有基于debian镜像的 1/3。

使用 Alpine镜像有个注意点，就是它是基于 muslc的（glibc的替代标准库），这两个库实现了相同的内核接口。其中 glibc 更常见，速度更快，而 muslic 使用较少的空间，侧重于安全性。在编译应用程序时，大部分都是针对特定的 libc 进行编译的。如果我们要将它们与另一个 libc 一起使用，则必须重新编译它们。换句话说，基于 Alpine 基础镜像构建容器可能会导致非预期的行为，因为标准 C 库是不一样的。不过，这种情况比较难碰到，即使碰到也有解决方法[7]。

### 删除RUN的缓存文件

linux中大部分包管理软件都需要更新源，该操作会带来一些缓存文件，这里记录了常用的清理方法。

•基于debian的镜像

```
# 换国内源，并更新
sed -i “s/deb.debian.org/mirrors.aliyun.com/g” /etc/apt/sources.list && apt update
# --no-install-recommends 很有用
apt install -y --no-install-recommends a b c && rm -rf /var/lib/apt/lists/*
```

  •alpine镜像

```
# 换国内源，并更新
sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories
# --no-cache 表示不缓存
apk add --no-cache a b c && rm -rf /var/cache/apk/*
```

 •centos镜像

```
# 换国内源并更新
curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo && yum makecache
yum install -y a b c  && yum clean al
```







## Dockfile实践

### 最佳实践点

•编写.dockerignore文件•一个容器只运行单个应用•基础镜像和生产镜像的标签不要使用latest•设置WORKDIR和CMD•使用ENTRYPOINT，并用exec启动命令（可选）•相比ADD，优先使用COPY•设置默认的环境变量，映射端口和数据卷•使用LABEL设置镜像元数据•添加HEALTHCHECK

### 多阶段构建样例

```
FROM golang:1.11-alpine AS build

# 安装项目所需工具
# Run `docker build --no-cache .` to update dependencies
RUN apk add --no-cache git
RUN go get github.com/golang/dep/cmd/dep

# 安装项目的依赖库（GO使用 Gopkg.toml and Gopkg.lock）
# These layers are only re-built when Gopkg files are updated
COPY Gopkg.lock Gopkg.toml /go/src/project/
WORKDIR /go/src/project/
# Install library dependencies
RUN dep ensure -vendor-only

# 拷贝项目并进行构建
# This layer is rebuilt when a file changes in the project directory
COPY . /go/src/project/
RUN go build -o /bin/project

# 精简的生成环境
FROM scratch
COPY --from=build /bin/project /bin/project
ENTRYPOINT ["/bin/project"]
CMD ["--help"]
```

## 常见问题

### alpine基础镜像使用

1.解决glic问题

```
ENV ALPINE_GLIBC_VERSION="2.31-r0"
ENV LANG=C.UTF-8

RUN set -x \
    && sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories \
    && apk add --no-cache wget \
    && wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub \
    && wget -O https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$ALPINE_GLIBC_VERSION/glibc-$ALPINE_GLIBC_VERSION.apk \
    && wget -O https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$ALPINE_GLIBC_VERSION/glibc-$ALPINE_GLIBC_VERSION.apk \
    && wget -O https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$ALPINE_GLIBC_VERSION/glibc-bin-$ALPINE_GLIBC_VERSION.apk \
    && wget -O https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$ALPINE_GLIBC_VERSION/glibc-i18n-$ALPINE_GLIBC_VERSION.apk \
    && apk add --no-cache glibc-$ALPINE_GLIBC_VERSION.apk  \
                    glibc-bin-$ALPINE_GLIBC_VERSION.apk \
                    glibc-i18n-$ALPINE_GLIBC_VERSION.apk \
    && /usr/glibc-compat/bin/localedef --force --inputfile POSIX --charmap UTF-8 "$LANG" || true \
    && echo "export LANG=$LANG" &gt; /etc/profile.d/locale.sh \
    && apk del glibc-i18n \
    && rm glibc-$ALPINE_GLIBC_VERSION.apk glibc-bin-$ALPINE_GLIBC_VERSION.apk glibc-i18n-$ALPINE_GLIBC_VERSION.apk
```



## 参考文献

1.Dockerfile最佳实践[8]2.docker多阶段构建[9]3.三个技巧，将 Docker 镜像体积减小 90%[10]4.精简Docker镜像的五种通用方法[11]5.优化Dockerfile最佳实践[12]6.alpine3.12镜像[13]

### References

`[1]` 官方的Dockerfile最佳实践: *https://docs.docker.com/develop/develop-images/dockerfile_best-practices/*
`[2]` alpine:3.12: *https://github.com/alpinelinux/docker-alpine/blob/90788e211ec6d5df183d79d6cb02e068b258d198/x86_64/Dockerfile*
`[3]` Dockerfile: *https://blog.haojunyu.com/atts/redis_multiline*
`[4]` Dockerfile: *https://blog.haojunyu.com/atts/redis_singleline*
`[5]` Dockerfile: *https://blog.haojunyu.com/atts/redis_multistage*
`[6]` Dockerfile: *https://blog.haojunyu.com/atts/redis_official_buster*
`[7]` 解决方法: *#alpine基础镜像使用*
`[8]` Dockerfile最佳实践: *https://docs.docker.com/develop/develop-images/dockerfile_best-practices/*
`[9]` docker多阶段构建: *https://docs.docker.com/develop/develop-images/multistage-build/*
`[10]` 三个技巧，将 Docker 镜像体积减小 90%: *https://www.infoq.cn/article/3-simple-tricks-for-smaller-docker-images*
`[11]` 精简Docker镜像的五种通用方法: *https://zhuanlan.zhihu.com/p/42815689*
`[12]` 优化Dockerfile最佳实践: *https://blog.csdn.net/xyz_dream/article/details/89741751?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase*
`[13]` alpine3.12镜像: *https://github.com/alpinelinux/docker-alpine/blob/90788e211ec6d5df183d79d6cb02e068b258d198/x86_64/Dockerfile*