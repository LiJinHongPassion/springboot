JsutAuth是一个集成了众多第三方登录的依赖

该项目展示如何快速的使用第三方登录,以github为例子

title: Java-JustAuth第三方授权项目入门使用教程


1. 简述

应用场景：在搭建网站过程中，我们可能需要涉及到第三方登录，例如qq，微信，github，钉钉等；常规的办法是到相应的官网上去，集成第三方的sdk来实现授权登录。

解决方案：JustAuth集成了实际中应用的授权登录，能够快速的进行授权；

---

2. 知识理论

在这之前，你需要了解：

OAuth2.0：OAuth是一个关于授权（authorization）的开放网络标准

---

3. 授权流程

这里以GitHub为例子，需求是A网站需要集成GitHub的登录授权，流程图如下：



解释上图流程：

1. 针对不同的第三方平台，我们需要拥有他的平台账号进入他的开发者中心；
2. 进入他的开发者中心后，我们需要找到创建应用类似的栏目，然后将A网站添加进去，设置回调url，并记录下 id 和 secret；
3. 最后我们就只需要使用工具实现授权

---

4. 实例：GitHub

4.1. 获取账号

需要账号的自行去注册：https://github.com/



4.2. 创建应用

进入开发者设置：https://github.com/settings/developers





OAuth Apps -> New OAuth App





记住id和secret





4.3 使用JustAuth实现授权登陆

下载官方的demo：https://gitee.com/yadong.zhang/JustAuth-demo

流程：调用授权方法->确认授权->跳转回调url->返回授权信息



RestAuthController.java





RestAuthController.java中的getAuthRequest(String source)





RestAuthController.java中的login(@PathVariable("source") String source, AuthCallback callback)

授权成功后，调用回调地址，这里的回调地址设置的是login方法，authRequest.login(callback)会返回授权信息

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

AuthCallback：该对象用于接收回调地址栏返回的code，state等参数，





---

5.已集成平台

具体文档还是需参考官网的文档(https://docs.justauth.whnb.wang/#/README?id=已集成的平台）：

  平台           	API类                       	SDK 
  gitee        	AuthGiteeRequest           	参考文档
  github       	AuthGithubRequest          	参考文档
  weibo        	AuthWeiboRequest           	参考文档
  钉钉           	AuthDingTalkRequest        	参考文档
  百度           	AuthBaiduRequest           	参考文档
  coding       	AuthCodingRequest          	参考文档
  微云           	AuthTencentCloudRequest    	参考文档
  开源中国         	AuthOschinaRequest         	参考文档
  支付宝          	AuthAlipayRequest          	参考文档
  qq           	AuthQqRequest              	参考文档
  微信           	AuthWeChatRequest          	参考文档
  淘宝           	AuthTaobaoRequest          	参考文档
  google       	AuthGoogleRequest          	参考文档
  facebook     	AuthFacebookRequest        	参考文档
  抖音           	AuthDouyinRequest          	参考文档
  linkedin     	AuthLinkedinRequest        	参考文档
  微软           	AuthMicrosoftRequest       	参考文档
  小米           	AuthMiRequest              	参考文档
  头条           	AuthToutiaoRequest         	参考文档
  teambition   	AuthTeambitionRequest      	参考文档
  人人           	AuthRenrenRequest          	参考文档
  pinterest    	AuthPinterestRequest       	参考文档
  stackoverflow	AuthStackOverflowRequest   	参考文档
  华为           	AuthHuaweiRequest          	参考文档
  企业微信         	AuthWeChatEnterpriseRequest	参考文档
  CSDN         	AuthCsdnRequest            	无   

---

参考文献

https://gitee.com/yadong.zhang/JustAuth-demo	JustAuth的springboot-demo

https://gitee.com/yadong.zhang/JustAuth	JustAuth的官方项目地址

https://docs.justauth.whnb.wang/#/README	JustAuth的说明文档




