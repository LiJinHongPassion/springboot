> 转载：https://blog.csdn.net/mytt_10566/article/details/100116670
参考：

插件GitHub地址：https://github.com/git-commit-id/maven-git-commit-id-plugin

git-commit-id-plugin 是一个类似于 buildnumber-maven-plugin 的插件，由于buildnumber-maven-plugin插件仅支持 CVS 和 SVN，所以作者就开发了一个支持Git版。这个插件主要有以下几个功能：

- 明确部署的版本
- 校验属性是否符合预期值

一、插件目标、参数
查看插件目标、绑定阶段、参数等信息

help插件的describe目标：http://maven.apache.org/plugins/maven-help-plugin/：
mvn help:describe -Dplugin=pl.project13.maven:git-commit-id-plugin:2.2.5 -Ddetail
或者直接查看官方文档：https://github.com/git-commit-id/maven-git-commit-id-plugin/blob/master/docs/using-the-plugin.md
这个插件一共有2个目标：

git-commit-id:revision：将构建时的信息保存到指定文件中或maven的属性中，默认绑定生命周期的阶段（phase）：initialize
git-commit-id:validateRevision：校验属性是否符合预期值，默认绑定阶段：verify

1.1 git-commit-id:revision目标
将构建时的信息保存到指定文件中或maven的属性中，默认绑定生命周期的阶段（phase）：initialize


相关参数如下：

|参数	|默认值	|描述|
| ---- | ---- | ---- |
|abbrevLength|	7|	git.commit.id.abbrev属性的长度，取值范围在[2, 40]，即打印的object id的长度|
|commitIdGenerationMode	|flat	|git.commit.id属性的生成模式，可选值：flat、full|
|dateFormat	|yyyy-MM-dd’T’HH:mm:ssZ	|定义这个插件的使用日期的格式|
|dateFormatTimeZone	|	|定义日期时区，如：America/Los_Angeles、GMT+10、PST|
|dotGitDirectory	|${project.basedir}/.git|	需要检查的仓库根目录|
|evaluateOnCommit|	HEAD|	告诉插件生成属性参考哪次提交|
|failOnNoGitDirectory|	true|	没有 .git 目录时则构建失败，false：继续构建|
|failOnUnableToExtractRepoInfo|	true|	获取不到足够数据则构建失败，false：继续构建|
|format	|properties| 	保存属性的格式：properties 或 json|
|generateGitPropertiesFile|	false|	true：生成git.properties文件，默认只是将属性添加到maven项目属性中|
|generateGitPropertiesFilename|	${project.build.outputDirectory}/git.properties)	|git.properties文件的位置，在generateGitPropertiesFile=true时有效|
|gitDescribe|		|配置git-describe命令，可以修改dirty标志、abbrev长度、其他可选项|
|excludeProperties|	|	需要隐藏的属性列表，如：仓库地址git.remote.origin.url、用户信息git.commit.user.*|
|includeOnlyProperties|	|	需要保存到文件中的属性列表，该列表会被excludeProperties属性配置的值覆盖|
|injectAllReactorProjects|	false|	true：将git属性注入所有项目|
|prefix	|git	|属性的前缀，如生成的属性都是：git.xxx|
|replacementProperties|		|在生成的git属性中通过表达式替换字符或字符串|
|runOnlyOnce|	false|	true：在多模块构建中只执行一次|
|skip	|false|	true：跳过插件执行|
|skipPoms|	true|	false：执行以pom方式打包的项目|
|skipViaCommandLine|	false	|true：通过命令行方式跳过插件执行，属性：maven.gitcommitid.skip|
|useNativeGit|	false|	true：使用原生git去获取仓库信息|
|verbose	|false|	true：扫描路径时打印更多信息|


1.2 git-commit-id:validateRevision目标
校验属性是否符合预期值，默认绑定阶段：verify

相关参数如下：

|参数	|默认值|	描述|
| ---- | ---- | ---- |
|validationProperties|	|	需要校验的属性|
|validationShouldFailIfNoMatch	|true	|true：不匹配时则构建失败|

二、配置示例
2.1 生成git.properties文件
需求：

获取git仓库以及提交相关信息，在运行时可以动态获取
实现：

通过配置<generateGitPropertiesFile>为true，生成git.properties文件，保存至编译后的classes目录下
提供一个接口，获取git.properties文件属性
示例：

（1）插件配置

默认和生命周期的initialize阶段绑定，所以在initialize阶段后任一阶段如：compile、package等，都会生成git.properties文件
```xml
<plugin>
    <groupId>pl.project13.maven</groupId>
    <artifactId>git-commit-id-plugin</artifactId>
    <version>2.2.5</version>
    <executions>
        <execution>
            <id>get-the-git-infos</id>
            <!-- 默认绑定阶段initialize -->
            <phase>initialize</phase>
            <goals>
            	<!-- 目标：revision -->
                <goal>revision</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
    	<!-- 检查的仓库根目录，${project.basedir}：项目根目录，即包含pom.xml文件的目录 -->
        <dotGitDirectory>${project.basedir}/.git</dotGitDirectory>
        <!-- false：扫描路径时不打印更多信息，默认值false，可以不配置 -->
        <verbose>false</verbose>
        <!-- 定义插件中所有时间格式，默认值：yyyy-MM-dd’T’HH:mm:ssZ -->
        <dateFormat>yyyy-MM-dd HH:mm:ss</dateFormat>
        <!-- git属性文件中各属性前缀，默认值git，可以不配置 -->
        <prefix>git</prefix>
        <!-- 生成git属性文件，默认false：不生成 -->
        <generateGitPropertiesFile>true</generateGitPropertiesFile>
        <!-- 生成git属性文件路径及文件名，默认${project.build.outputDirectory}/git.properties -->
        <generateGitPropertiesFilename>${project.build.outputDirectory}/git.properties</generateGitPropertiesFilename>
        <!-- 生成git属性文件格式，默认值properties -->
        <format>json</format>
        <!-- 配置git-describe命令 -->
        <gitDescribe>
            <skip>false</skip>
            <always>false</always>
            <dirty>-dirty</dirty>
        </gitDescribe>
    </configuration>
</plugin>
```

（2）获取git.properties文件属性

定义一个Controller，读取git.properties文件即可。
```java
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/version")
public class VersionController {

	@GetMapping("/info")
    public Map<String, Object> getVersionInfo() {
        return readGitProperties();
    }

    private Map<String, Object> readGitProperties() {
        InputStream inputStream = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            inputStream = classLoader.getResourceAsStream("git.properties");
			// 读取文件内容，自定义一个方法实现即可
            String versionJson = FileUtils.getStringFromStream(inputStream);
            JSONObject jsonObject = JSON.parseObject(versionJson);
            Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
            if (CollectionUtils.isNotEmpty(entrySet)) {
                return entrySet.stream()
                		.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (o, n) -> n));
            }
        } catch (Exception e) {
            log.error("get git version info fail", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error("close inputstream fail", e);
            }
        }
        return new HashMap<>();
    } 
}
```

（3）测试

启动项目，在编译后的classes目录下可以看到git.properties文件：
```json
{
"git.branch" : "master",
"git.build.host" : "localhost",
"git.build.time" : "2019-08-28 17:05:33",
"git.build.user.email" : "xxx@163.com",
"git.build.user.name" : "xxx",
"git.build.version" : "1.0-SNAPSHOT",
"git.closest.tag.commit.count" : "",
"git.closest.tag.name" : "",
"git.commit.id" : "437e26172c51cab8fc88ea585145797df222fbb2",
"git.commit.id.abbrev" : "437e261",
"git.commit.id.describe" : "437e261-dirty",
"git.commit.id.describe-short" : "437e261-dirty",
"git.commit.message.full" : "获取版本信息",
"git.commit.message.short" : "获取版本信息",
"git.commit.time" : "2019-08-27 19:07:03",
"git.commit.user.email" : "xxx@163.com",
"git.commit.user.name" : "xxx",
"git.dirty" : "true",
"git.remote.origin.url" : "http://git.xxx.cn/gitlab/git/xxx.git",
"git.tags" : "",
"git.total.commit.count" : "3324"
}
```
访问步骤2中定义的接口，即可获取到git.properties文件中属性。


2.2 配置打包名称
需求：

默认打包文件名格式为：<artifactId>-<version>.jar（没有通过<finalName>标签指定），由于version不是都需要改动，所以需要额外参数来控制版本
实现：

获取git最后一次提交版本号，拼接在version后，属性：git.commit.id.abbrev（由插件将该属性注入到maven项目中，可以在pom文件中引用）
示例：

（1）pom.xml配置如下：
```xml
<groupId>com.momo</groupId>
<artifactId>myproject</artifactId>
<!-- 拼接最后一次git提交的版本号，默认7位 -->
<version>1.0.0-${git.commit.id.abbrev}</version>

<build>
    <plugins>
    	<!-- git-commit-id插件，配置同示例2.1 -->
    	<plugin>
            <groupId>pl.project13.maven</groupId>
            <artifactId>git-commit-id-plugin</artifactId>
            <version>2.2.5</version>
            <executions>
                <execution>
                    <id>get-the-git-infos</id>
                    <phase>initialize</phase>
                    <goals>
                        <goal>revision</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <dotGitDirectory>${project.basedir}/.git</dotGitDirectory>
                <prefix>git</prefix>
                <verbose>false</verbose>
                <dateFormat>yyyy-MM-dd HH:mm:ss</dateFormat>
                <generateGitPropertiesFile>true</generateGitPropertiesFile>
                <generateGitPropertiesFilename>${project.build.outputDirectory}/git.properties</generateGitPropertiesFilename>
                <format>json</format>
                <!-- git.commit.id.abbrev属性值的长度，取值范围在[2, 40]，默认值7 -->
                <abbrevLength>7</abbrevLength>
                <gitDescribe>
                    <skip>false</skip>
                    <always>false</always>
                    <dirty>-dirty</dirty>
                </gitDescribe>
            </configuration>
        </plugin>
    </plugins>
</build>
```

（2）执行package命令

可以看到打包的文件名为：myproject-1.0.0-a30b2ff.jar

若将abbrevLength属性值修改为10，则文件名：myproject-1.0.0-a30b2ffd11.jar
2.3 校验Git属性
需求：

验证生成的git属性文件中的属性，如果和期望值不匹配则构建失败
如 git.dirty 属性，期望值：false（true：仓库脏，如修改后的文件没有commit，false：表示仓库干净）
示例：

（1）pom中插件配置如下：
```xml
<plugin>
    <groupId>pl.project13.maven</groupId>
    <artifactId>git-commit-id-plugin</artifactId>
    <version>2.2.5</version>
    <executions>
        <execution>
            <id>get-the-git-infos</id>
            <phase>initialize</phase>
            <goals>
                <goal>revision</goal>
            </goals>
        </execution>
        <!-- 绑定validateRevision目标到package阶段 -->
        <execution>
            <id>validate-the-git-infos</id>
            <phase>package</phase>
            <goals>
                <goal>validateRevision</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <dotGitDirectory>${project.basedir}/.git</dotGitDirectory>
        <verbose>false</verbose>
        <dateFormat>yyyy-MM-dd HH:mm:ss</dateFormat>
        <prefix>git</prefix>
        <generateGitPropertiesFile>true</generateGitPropertiesFile>
        <generateGitPropertiesFilename>${project.build.outputDirectory}/git.properties</generateGitPropertiesFilename>
        <format>json</format>
        <gitDescribe>
            <skip>false</skip>
            <always>false</always>
            <dirty>-dirty</dirty>
        </gitDescribe>
		<!-- 定义需要校验的属性 -->	
        <validationProperties>
            <validationProperty>
            	<!-- 校验失败时提示使用 -->
                <name>validating git dirty</name>
                <!-- 需要校验的属性 -->
                <value>${git.dirty}</value>
                <!-- 期望的属性值：false -->
                <shouldMatchTo>false</shouldMatchTo>
            </validationProperty>
        </validationProperties>
        <!-- 配置校验的属性值与期望值不一致是否构建失败，默认值true：失败，false：继续构建 -->
        <validationShouldFailIfNoMatch>true</validationShouldFailIfNoMatch>
    </configuration>
</plugin>
```
（2）测试

测试1：修改一个文件不commit，然后执行package命令，此时会构建失败：
[ERROR] Failed to execute goal pl.project13.maven:git-commit-id-plugin:2.2.5:validateRevision (validate-the-git-infos) on project xxx: Validation ‘validating git dirty’ failed! Expected ‘true’ to match with ‘false’! -> [Help 1]
测试2：修改一个文件不commit，将<shouldMatchTo>属性值改为true，此时构建成功
测试3：修改一个文件不commit，将<validationShouldFailIfNoMatch>属性值修改为false，此时构建也可以成功
注：

校验项目名称、提交是否有tag示例（都是来自于官方文档）：
```xml
<validationProperties>
  <!-- 校验项目版本是否以`-SNAPSHOT`结尾 -->
  <validationProperty>
    <name>validating project version</name>
    <value>${project.version}</value>
    <shouldMatchTo><![CDATA[^.*(?<!-SNAPSHOT)$]]></shouldMatchTo>
  </validationProperty>

  <!-- 校验当前提交是否有tag -->
  <validationProperty>
    <name>validating current commit has a tag</name>
    <value>${git.closest.tag.commit.count}</value>
    <shouldMatchTo>0</shouldMatchTo>
   </validationProperty>
</validationProperties>
```

注：详细参数说明参考官方文档，以上翻译仅供参考。