JsutAuth是一个集成了众多第三方登录的依赖

该项目展示如何快速的使用第三方登录,以github为例子

title: Java-JustAuth第三方授权项目入门使用教程


------

![](https://images.unsplash.com/photo-1556742205-e10c9486e506?ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80)

<!-- more -->

<center>

​	<span id="m_name"></span>

​	<audio id="m_url_id" controls> <source src=""> <source src="horse.ogg" type="audio/ogg"> Your browser does not support this audio format. 

​	</audio> 

</center>  

------

## 1. 简述

应用场景：在搭建网站过程中，我们可能需要涉及到第三方登录，例如qq，微信，github，钉钉等；常规的办法是到相应的官网上去，集成第三方的sdk来实现授权登录。

解决方案：JustAuth集成了实际中应用的授权登录，能够快速的进行授权；

个人demo：https://github.com/LiJinHongPassion/springboot/tree/master/springboot_justauth_demo

---

## 2. 知识理论

在这之前，你需要了解：

**[OAuth2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)**：OAuth是一个关于授权（authorization）的开放网络标准

---

## 3. 授权流程

这里以GitHub为例子，需求是A网站需要集成GitHub的登录授权，流程图如下：

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle5/1.png)

**解释上图流程：**

1. 针对不同的第三方平台，我们需要拥有他的平台账号进入他的开发者中心；
2. 进入他的开发者中心后，我们需要找到**创建应用**类似的栏目，然后将A网站添加进去，设置回调url，并记录下 id 和 secret；
3. 最后我们就只需要使用工具实现授权

---

## 4. 实例：GitHub

#### 4.1. 获取账号

需要账号的自行去注册：https://github.com/



#### 4.2. 创建应用

进入开发者设置：https://github.com/settings/developers

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle5/2.png)



OAuth Apps -> New OAuth App

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle5/3.png)



记住id和secret

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle5/4.png)



#### 4.3 使用`JustAuth`实现授权登陆

下载官方的demo：https://gitee.com/yadong.zhang/JustAuth-demo

流程：调用授权方法->确认授权->跳转回调url->返回授权信息



**RestAuthController.java**

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle5/6.png)



**RestAuthController.java**中的`getAuthRequest(String source)`

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle5/5.png)



**RestAuthController.java**中的`login(@PathVariable("source") String source, AuthCallback callback)`

授权成功后，调用回调地址，这里的回调地址设置的是login方法，authRequest.login(callback)会返回授权信息

```java
/**
     * oauth平台中配置的授权回调地址，以本项目为例，在创建github授权应用时的回调地址应为：http://127.0.0.1:8443/oauth/callback/github
     */
    @RequestMapping("/callback/{source}")
    public Object login(@PathVariable("source") String source, AuthCallback callback) {
        System.out.println("进入callback：" + source + " callback params：" + JSONObject.toJSONString(callback));
        AuthRequest authRequest = getAuthRequest(source);
        AuthResponse response = authRequest.login(callback);
        System.out.println(JSONObject.toJSONString(response));
        return response;
    }
```

**AuthCallback**：该对象用于接收回调地址栏返回的code，state等参数，

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle5/9.png)

![](https://raw.githubusercontent.com/LiJinHongPassion/LiJinHongPassion.github.io/master/codeant/dle5/8.png)

---

## 5.已集成平台

具体文档还是需参考官网的文档(https://docs.justauth.whnb.wang/#/README?id=已集成的平台）：

| 平台          | API类                                                        | SDK                                                          |
| ------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| gitee         | [AuthGiteeRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthGiteeRequest.java) | [参考文档](https://gitee.com/api/v5/oauth_doc#list_1)        |
| github        | [AuthGithubRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthGiteeRequest.java) | [参考文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/) |
| weibo         | [AuthWeiboRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthGiteeRequest.java) | [参考文档](https://open.weibo.com/wiki/授权机制说明)         |
| 钉钉          | [AuthDingTalkRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthDingTalkRequest.java) | [参考文档](https://open-doc.dingtalk.com/microapp/serverapi2/kymkv6) |
| 百度          | [AuthBaiduRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthBaiduRequest.java) | [参考文档](http://developer.baidu.com/wiki/index.php?title=docs/oauth) |
| coding        | [AuthCodingRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthCodingRequest.java) | [参考文档](https://open.coding.net/references/oauth/)        |
| 微云          | [AuthTencentCloudRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthTencentCloudRequest.java) | [参考文档](https://dev.tencent.com/help/doc/faq/b4e5b7aee786/oauth) |
| 开源中国      | [AuthOschinaRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthOschinaRequest.java) | [参考文档](https://www.oschina.net/openapi/docs/oauth2_authorize) |
| 支付宝        | [AuthAlipayRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthAlipayRequest.java) | [参考文档](https://alipay.open.taobao.com/docs/doc.htm?spm=a219a.7629140.0.0.336d4b70GUKXOl&treeId=193&articleId=105809&docType=1) |
| qq            | [AuthQqRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthQqRequest.java) | [参考文档](https://wiki.connect.qq.com/使用authorization_code获取access_token) |
| 微信          | [AuthWeChatRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthWeChatRequest.java) | [参考文档](https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN) |
| 淘宝          | [AuthTaobaoRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthTaobaoRequest.java) | [参考文档](https://open.taobao.com/doc.htm?spm=a219a.7386797.0.0.4e00669acnkQy6&source=search&docId=105590&docType=1) |
| google        | [AuthGoogleRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthGoogleRequest.java) | [参考文档](https://developers.google.com/identity/protocols/OpenIDConnect) |
| facebook      | [AuthFacebookRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthFacebookRequest.java) | [参考文档](https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow) |
| 抖音          | [AuthDouyinRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthDouyinRequest.java) | [参考文档](https://www.douyin.com/platform/doc/m-2-1-1)      |
| linkedin      | [AuthLinkedinRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthLinkedinRequest.java) | [参考文档](https://docs.microsoft.com/zh-cn/linkedin/shared/authentication/authorization-code-flow?context=linkedin/context) |
| 微软          | [AuthMicrosoftRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthMicrosoftRequest.java) | [参考文档](https://docs.microsoft.com/zh-cn/graph/auth/)     |
| 小米          | [AuthMiRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthMiRequest.java) | [参考文档](https://dev.mi.com/console/doc/detail?pId=711)    |
| 头条          | [AuthToutiaoRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthToutiaoRequest.java) | [参考文档](https://open.mp.toutiao.com/#/resource?_k=y7mfgk) |
| teambition    | [AuthTeambitionRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthTeambitionRequest.java) | [参考文档](https://docs.teambition.com/)                     |
| 人人          | [AuthRenrenRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthRenrenRequest.java) | [参考文档](http://open.renren.com/wiki/OAuth2.0)             |
| pinterest     | [AuthPinterestRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthPinterestRequest.java) | [参考文档](https://developers.pinterest.com/docs/api/overview/?) |
| stackoverflow | [AuthStackOverflowRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthStackOverflowRequest.java) | [参考文档](https://api.stackexchange.com/docs/authentication) |
| 华为          | [AuthHuaweiRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthHuaweiRequest.java) | [参考文档](https://developer.huawei.com/consumer/cn/devservice/doc/30101) |
| 企业微信      | [AuthWeChatEnterpriseRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthWeChatEnterpriseRequest.java) | [参考文档](https://open.work.weixin.qq.com/api/doc#90000/90135/90664) |
| CSDN          | [AuthCsdnRequest](https://gitee.com/yadong.zhang/JustAuth/blob/master/src/main/java/me/zhyd/oauth/request/AuthCsdnRequest.java) | 无                                                           |

---

## 参考文献

https://gitee.com/yadong.zhang/JustAuth-demo	JustAuth的springboot-demo

https://gitee.com/yadong.zhang/JustAuth	JustAuth的官方项目地址

https://docs.justauth.whnb.wang/#/README	JustAuth的说明文档



