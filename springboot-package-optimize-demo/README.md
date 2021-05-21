

注意：<font color="red">`-Djava.ext.dir`该参数有坑-Djava.ext.dirs会覆盖Java本身的ext设置，java.ext.dirs指定的目录由ExtClassLoader加载器加载，如果您的程序没有指定该系统属性，那么该加载器默认加载`$JAVA_HOME/jre/lib/ext`目录下的所有jar文件。但如果你手动指定系统属性且忘了把`$JAVA_HOME/jre/lib/ext`路径给加上，那`ExtClassLoader`不会去加载`$JAVA_HOME/lib/ext`下面的jar文件，这意味着你将失去一些功能，例如java自带的加解密算法实现。
       OK问题分析到这儿，什么原因已经很明朗，解决方案也很简单，只需在改路径后面补上ext的路径即可！比如： `-Djava.ext.dirs=./plugin:$JAVA_HOME/jre/lib/ext`。windows环境下运行程序，应该用分号替代冒号来分隔。
</font>

# Spring Boot部署JAR文件瘦身优化经验分享（转载）

本文截取代码片段来自于对应的完整示例源码工程：

* [https://gitee.com/xautlx/package-optimize-demo](https://gitee.com/xautlx/package-optimize-demo)

* [https://github.com/xautlx/package-optimize-demo](https://github.com/xautlx/package-optimize-demo)

相关代码和配置均实际执行测试过（基于OpenJDK8环境，其余JDK未做验证，如-Djava.ext.dirs=lib等参数和JDK版本有关，注意对应调整），如在验证过程发现有任何问题可Issue反馈以便及时更正，感谢支持！

## 概要说明

随着Spring Boot的流行，大家体验到只需构建输出一个jar文件，然后只需一个java -jar命令就能部署运行应用的爽快。
常见一些单体应用随着项目规模的扩展单个jar文件的大小越来越大，动辄两三百MB。
如果再引入微服务架构，动辄一二十个微服务，所有模块jar加起来整个系统光部署文件就一两个GB。

一个系统一旦上线运行，无论新需求迭代还是Bug修复，免不了需要做部署更新，尤其对于一些交付类型项目，首次部署或异地更新，
动不动就需要传输几百MB或几个GB的部署文件，确实是一个让人头疼的问题。

可以想象一下，线上系统发现一个紧急严重Bug捅到了主管那里，交代马上紧急修复解决，研发同事火速分析排查分分钟搞定提交代码并完成构建打包并交付给运维。
过一会领导着急上火来过问问题更新解决了吗？运维只能很尴尬的回答：还没呢，部署包文件比较大，正在上传有点慢...

一听领导就火了，就改了几行代码，部署更新为啥要上传几百MB的文件呢？难道没有办法优化一下吗？

遇到这样的情况，建议你往下看，或许能找到你想要的答案。

**本文内容包括：**

* 如何把一两百MB的单一Spring Boot jar文件，分离为依赖组件lib目录和一个业务jar来进行部署，优化单个jar文件大小到一两百KB。。
* 如何把一二十个微服务高度重叠的依赖组件合并到单一lib目录和多个一两百KB的业务jar来进行部署，优化整个项目部署文件大小从一两个GB大小到两三百MB。

**本文内容不包括：**

* 不包括进行Spring Boot配置文件分离相关，一般简单采用通过指定active profile从外部yaml配置文件覆盖jar文件中配置即可或是采用Nacos等配置服务模式。
* 不包括Maven最佳实践用法，列入样例工程中出于演示方便的考虑比如把一些本应放到各个Boot模块特定的配置声明直接放到顶层的parent中定义，请注意按实际情况优化调整使用。
* 不包括可执行jar的运行模式支持参考，文中实现方式主要面向java -jar运行模式。

## 瘦身打怪升级过程

### Level 0：常规的Fat Jar构建

参考项目目录：package-optimize-level0

**主要配置：**

```xml
<build>
    <finalName>${project.artifactId}</finalName>
    <!--
    特别注意：
    项目仅仅是为了演示配置方便，直接在parent的build部分做了插件配置和运行定义。
    但是实际项目中需要把这些定义只放到spring boot模块项目（可优化使用pluginManagement形式），避免干扰其他util、common等模块项目
    -->
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

**配置输出：**

```
cd package-optimize-level0
mvn clean install

ls -lh package-optimize-app1/target/package-optimize-app1.jar
-rw-r--r--  1 lixia  wheel    16M Feb 24 21:06 package-optimize-app1/target/package-optimize-app1.jar

java -jar package-optimize-app1/target/package-optimize-app1.jar
```

**重点说明：**

* （当前演示应用仅依赖了spring-boot-starter-web极少组件，所有构建输出只有十来MB）实际情况单一构建根据项目依赖组件量输出jar一般在几十MB到一两百MB甚至更大。
* 假如有十来个微服务需要部署，那就意味着需要传输一两个GB的文件，耗时可想而知。就算是单一更新个别微服务也需要传输一两百MB。

### Level 1：常见的依赖jar分离构建方式

参考项目目录：package-optimize-level1

**关解决问题：**

* 降低单个微服务jar的文件大小，以便部署过程秒传文件。

**主要配置：**

重点配置说明请详见如下注释说明：

```xml
<build>
    <finalName>${project.artifactId}</finalName>
    <!--
    特别注意：
    项目仅仅是为了演示配置方便，直接在parent的build部分做了插件配置和运行定义。
    但是实际项目中需要把这些定义只放到spring boot模块项目（可优化使用pluginManagement形式），避免干扰其他util、common等模块项目
    -->
    <plugins>
        <!-- 拷贝项目所有依赖jar文件到构建lib目录下 -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-dependency-plugin</artifactId>
            <executions>
                <execution>
                    <id>copy-dependencies</id>
                    <phase>package</phase>
                    <goals>
                        <goal>copy-dependencies</goal>
                    </goals>
                    <configuration>
                        <outputDirectory>${project.build.directory}/lib</outputDirectory>
                        <excludeTransitive>false</excludeTransitive>
                        <stripVersion>false</stripVersion>
                        <silent>true</silent>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        <!-- Spring Boot模块jar构建 -->
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <includes>
                    <!-- 不存在的include引用，相当于排除所有maven依赖jar，没有任何三方jar文件打入输出jar -->
                    <include>
                        <groupId>null</groupId>
                        <artifactId>null</artifactId>
                    </include>
                </includes>
                <layout>ZIP</layout>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

**配置输出：**

```
cd package-optimize-level1
mvn clean install

ls -lh package-optimize-app1/target/package-optimize-app1.jar
-rw-r--r--  1 lixia  wheel   149K Feb 24 20:56 package-optimize-app1/target/package-optimize-app1.jar

java -jar -Djava.ext.dirs=lib package-optimize-app1/target/package-optimize-app1.jar
```


**实现效果：**

* 单一构建根据项目依赖组件量输出jar一般仅有一两百KB，基本可以做到秒传。
* 这个是网上可见最常见的优化方案，还值得继续深入：假如有十来个微服务，每个服务一个jar和一个lib目录文件，首次部署也差不多需要传输一两个GB文件。

### Level 2：合并所有模块依赖jar到同一个lib目录

参考项目目录：package-optimize-level2

**解决问题：**

* 合并所有模块依赖jar到同一个lib目录，一般由于各模块项目依赖jar重叠程度很高，合并所有服务部署文件总计大小基本也就两三百MB
* 但是如果采用-Djava.ext.dirs=lib加载所有jar到每个JVM，一来每个JVM都完整加载了所有jar耗费资源，二来各微服务组件版本不同会出现版本冲突问题

**主要配置：**

重点配置说明请详见如下注释说明：

```xml
<build>
    <finalName>${project.artifactId}</finalName>
    <!--
    特别注意：
    项目仅仅是为了演示配置方便，直接在parent的build部分做了插件配置和运行定义。
    但是实际项目中需要把这些定义只放到spring boot模块项目（可优化使用pluginManagement形式），避免干扰其他util、common等模块项目
    -->
    <plugins>
        <!-- 基于maven-jar-plugin插件实现把依赖jar定义写入输出jar的META-INFO/MANIFEST文件 -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <configuration>
                <archive>
                    <manifest>
                        <addClasspath>true</addClasspath>
                        <classpathPrefix>lib/</classpathPrefix>
                        <useUniqueVersions>false</useUniqueVersions>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
        <!-- 拷贝项目所有依赖jar文件到构建lib目录下 -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-dependency-plugin</artifactId>
            <executions>
                <execution>
                    <id>copy-dependencies</id>
                    <phase>package</phase>
                    <goals>
                        <goal>copy-dependencies</goal>
                    </goals>
                    <configuration>
                        <!--
                        各子模块按照实际层级定义各模块对应的属性值，检查所有微服务模块依赖jar文件合并复制到同一个目录
                        详见各子模块中 boot-jar-output 属性定义
                        -->
                        <outputDirectory>${boot-jar-output}/lib</outputDirectory>
                        <excludeTransitive>false</excludeTransitive>
                        <stripVersion>false</stripVersion>
                        <silent>false</silent>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        <!-- Spring Boot模块jar构建 -->
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <includes>
                    <!-- 不存在的include引用，相当于排除所有maven依赖jar，没有任何三方jar文件打入输出jar -->
                    <include>
                        <groupId>null</groupId>
                        <artifactId>null</artifactId>
                    </include>
                </includes>
                <layout>ZIP</layout>
                <!--
                基于maven-jar-plugin输出微服务jar文件进行二次spring boot重新打包文件的输出目录
                所有微服务构建输出jar文件统一输出到与lib同一个目录，便于共同引用同一个lib目录
                详见各子模块中boot-jar-output属性定义
                -->
                <!--  -->
                <outputDirectory>${boot-jar-output}</outputDirectory>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

所有lib目录文件及各微服务构建jar聚合到devops公共目录。

微服务jar文件中的META-INFO/MANIFEST文件中会生成根据模块依赖组件列表的Class-Path属性，
从而避免了不同版本jar：

```
Class-Path: lib/spring-boot-starter-web-2.4.3.jar lib/spring-boot-starte
 r-2.4.3.jar lib/spring-boot-2.4.3.jar lib/spring-boot-autoconfigure-2.4
 .3.jar lib/spring-boot-starter-logging-2.4.3.jar lib/logback-classic-1.
 2.3.jar lib/logback-core-1.2.3.jar lib/slf4j-api-1.7.30.jar lib/log4j-t
 o-slf4j-2.13.3.jar lib/log4j-api-2.13.3.jar lib/jul-to-slf4j-1.7.30.jar
  lib/jakarta.annotation-api-1.3.5.jar lib/spring-core-5.3.4.jar lib/spr
 ing-jcl-5.3.4.jar lib/snakeyaml-1.27.jar lib/spring-boot-starter-json-2
 .4.3.jar lib/jackson-databind-2.11.4.jar lib/jackson-annotations-2.11.4
 .jar lib/jackson-core-2.11.4.jar lib/jackson-datatype-jdk8-2.11.4.jar l
 ib/jackson-datatype-jsr310-2.11.4.jar lib/jackson-module-parameter-name
 s-2.11.4.jar lib/spring-boot-starter-tomcat-2.4.3.jar lib/tomcat-embed-
 core-9.0.43.jar lib/jakarta.el-3.0.3.jar lib/tomcat-embed-websocket-9.0
 .43.jar lib/spring-web-5.3.4.jar lib/spring-beans-5.3.4.jar lib/spring-
 webmvc-5.3.4.jar lib/spring-aop-5.3.4.jar lib/spring-context-5.3.4.jar 
 lib/spring-expression-5.3.4.jar
```

**配置输出：**

```
cd package-optimize-level2
mvn clean install

ls -lh devops/
total 912
drwxr-xr-x  34 lixia  wheel   1.1K Feb 24 22:27 lib
-rw-r--r--   1 lixia  wheel   150K Feb 24 22:31 package-optimize-app1.jar
-rw-r--r--   1 lixia  wheel   149K Feb 24 22:31 package-optimize-app2.jar
-rw-r--r--   1 lixia  wheel   149K Feb 24 22:31 package-optimize-app3.jar

java -jar devops/package-optimize-app1.jar
```


**实现效果：**

* 启动过程不再需要 -Djava.ext.dirs=lib 参数定义。
* 所有微服务jar引用所有项目合并依赖组件的公共目录，部署文件总计大小一般在两三百MB。
* 通过定制每个微服务jar文件中的META-INFO/MANIFEST文件中的Class-Path明确指明依赖版本组件类，解决各微服务不同组件版本冲突问题。

### Level 3：支持system引入的非官方的三方依赖组件

参考项目目录：package-optimize-level3

**解决问题：**

* 有些非官方三方的诸如sdk jar，一种做法是提交到Maven本地私服中去引用，那和普通依赖jar处理相同；
但是在没有maven私服的情况下，常见的简化做法都是直接在项目中放置依赖jar然后在pom中以system scope方式定义。
* 对于在pom中是以systemPath方式引入的，maven-jar-plugin组件没有直接参数声明包含指定scope的组件，
如果不做特殊处理META-INFO/MANIFEST中不会出现这些scope定义的组件，导致运行时类找不到。

**主要配置：**

重点配置说明请详见如下注释说明：

```xml
<build>
    <finalName>${project.artifactId}</finalName>
    <!--
    特别注意：
    项目仅仅是为了演示配置方便，直接在parent的build部分做了插件配置和运行定义。
    但是实际项目中需要把这些定义只放到spring boot模块项目（可优化使用pluginManagement形式），避免干扰其他util、common等模块项目
    -->
    <plugins>
        <!-- 基于maven-jar-plugin插件实现把依赖jar定义写入输出jar的META-INFO/MANIFEST文件 -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
            <configuration>
                <archive>
                    <manifest>
                        <addClasspath>true</addClasspath>
                        <classpathPrefix>lib/</classpathPrefix>
                        <useUniqueVersions>false</useUniqueVersions>
                    </manifest>
                    <manifestEntries>
                        <!--
                        有些非官方三方的诸如sdk jar在pom中是以systemPath方式引入的，maven-jar-plugin组件没有直接参数声明包含指定scope的组件
                        通过使用额外定义 Class-Path 值来追加指定依赖组件列表，在子模块按实际情况指定 jar-manifestEntries-classpath 值即可
                        例如(注意前面个点字符及各空格分隔符)：. lib/xxx-1.0.0.jar lib/yyy-2.0.0.jar
                        详见各子模块中 boot-jar-output 属性定义示例
                        -->
                        <Class-Path>${jar-manifestEntries-classpath}</Class-Path>
                    </manifestEntries>
                </archive>
            </configuration>
        </plugin>
        <!-- 拷贝项目所有依赖jar文件到构建lib目录下 -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-dependency-plugin</artifactId>
            <executions>
                <execution>
                    <id>copy-dependencies</id>
                    <phase>package</phase>
                    <goals>
                        <goal>copy-dependencies</goal>
                    </goals>
                    <configuration>
                        <!--
                        各子模块按照实际层级定义各模块对应的属性值，检查所有微服务模块依赖jar文件合并复制到同一个目录
                        详见各子模块中 boot-jar-output 属性定义
                        -->
                        <outputDirectory>${boot-jar-output}/lib</outputDirectory>
                        <excludeTransitive>false</excludeTransitive>
                        <stripVersion>false</stripVersion>
                        <silent>false</silent>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        <!-- Spring Boot模块jar构建 -->
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <includes>
                    <!-- 不存在的include引用，相当于排除所有maven依赖jar，没有任何三方jar文件打入输出jar -->
                    <include>
                        <groupId>null</groupId>
                        <artifactId>null</artifactId>
                    </include>
                </includes>
                <layout>ZIP</layout>
                <!--
                基于maven-jar-plugin输出微服务jar文件进行二次spring boot重新打包文件的输出目录
                所有微服务构建输出jar文件统一输出到与lib同一个目录，便于共同引用同一个lib目录
                详见各子模块中boot-jar-output属性定义
                -->
                <!--  -->
                <outputDirectory>${boot-jar-output}</outputDirectory>
            </configuration>
            <executions>
                <execution>
                    <goals>
                        <goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

子模块主要配置：

```
    <properties>
        <!-- 按各模块实际目录层次定义相对数据，使所有服务模块输出资源汇聚到相同目录 -->
        <boot-jar-output>../devops</boot-jar-output>
        <!--
        有些供应商的sdk jar在pom中是以systemPath方式引入的，maven-jar-plugin组件没有直接参数声明包含指定scope的组件
        通过使用额外定义 Class-Path 值来追加指定依赖组件列表，按实际情况指定 jar-manifestEntries-classpath 值即可
        例如(注意前面个点字符及各空格分隔符，lib后面部分是 artifactId-version.jar 格式而不是实际文件名)：. lib/xxx-1.0.0.jar lib/yyy-2.0.0.jar
        -->
        <jar-manifestEntries-classpath>. lib/hik-sdk-1.0.0.jar</jar-manifestEntries-classpath>
    </properties>
    <dependencies>
        <!-- 以相对路径方式定义非官方三方依赖组件 -->
        <dependency>
            <groupId>com.hik</groupId>
            <artifactId>hik-sdk</artifactId>
            <version>1.0.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/hik-sdk-1.0.0.jar</systemPath>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
```

微服务输出jar文件中的META-INFO/MANIFEST文件中会生成根据模块依赖组件列表的Class-Path属性，
最前面会追加 jar-manifestEntries-classpath 属性定义值：

```
Class-Path: . lib/hik-sdk-1.0.0.jar lib/spring-boot-starter-web-2.4.3.ja
 r lib/spring-boot-starter-2.4.3.jar lib/spring-boot-2.4.3.jar lib/sprin
 g-boot-autoconfigure-2.4.3.jar lib/spring-boot-starter-logging-2.4.3.ja
 r lib/logback-classic-1.2.3.jar lib/logback-core-1.2.3.jar lib/slf4j-ap
 i-1.7.30.jar lib/log4j-to-slf4j-2.13.3.jar lib/log4j-api-2.13.3.jar lib
 /jul-to-slf4j-1.7.30.jar lib/jakarta.annotation-api-1.3.5.jar lib/sprin
 g-core-5.3.4.jar lib/spring-jcl-5.3.4.jar lib/snakeyaml-1.27.jar lib/sp
 ring-boot-starter-json-2.4.3.jar lib/jackson-databind-2.11.4.jar lib/ja
 ckson-annotations-2.11.4.jar lib/jackson-core-2.11.4.jar lib/jackson-da
 tatype-jdk8-2.11.4.jar lib/jackson-datatype-jsr310-2.11.4.jar lib/jacks
 on-module-parameter-names-2.11.4.jar lib/spring-boot-starter-tomcat-2.4
 .3.jar lib/tomcat-embed-core-9.0.43.jar lib/jakarta.el-3.0.3.jar lib/to
 mcat-embed-websocket-9.0.43.jar lib/spring-web-5.3.4.jar lib/spring-bea
 ns-5.3.4.jar lib/spring-webmvc-5.3.4.jar lib/spring-aop-5.3.4.jar lib/s
 pring-context-5.3.4.jar lib/spring-expression-5.3.4.jar
```

**配置输出：**

```
cd package-optimize-level3
mvn clean install

ls -lh devops/
total 912
drwxr-xr-x  36 lixia  wheel   1.1K Feb 24 23:14 lib
-rw-r--r--@  1 lixia  wheel   150K Feb 24 23:14 package-optimize-app1.jar
-rw-r--r--   1 lixia  wheel   150K Feb 24 23:14 package-optimize-app2.jar
-rw-r--r--   1 lixia  wheel   150K Feb 24 23:14 package-optimize-app3.jar

java -jar devops/package-optimize-app1.jar
```


## 最终实现效果

* 所有服务的依赖组件合并到一个目录，总计大小在两三百MB，首次部署传输效率明显提速。
* 各微服务jar一两百KB大小，日常紧急修复Bug更新个别jar基本就是瞬间秒传。
* 各微服务jar中各自定义依赖指定版本组件列表，不会出现组件不同版本加载冲突问题。
* 非官方的三方依赖组件也能正常引用处理。

## 特别提示

上述通过部署组件分离处理后，日常更新只需要传输一两百KB的业务jar文件即可。
但是如果某个项目的maven依赖组件做了变更配置，则需要注意把变更的jar文件要同步到公共的lib目录。

最小化变更jar文件的小技巧：可以把构建部署资源目录提交到GIT库，以后每次版本发布同时commit到GIT库，
通过提交视图可以清晰的识别出lib目录下和业务jar本次版本发布的变更文件清单，包括微服务jar和依赖jar变更文件，以此最小化传输文件。
