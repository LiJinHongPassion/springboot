---
title: Java-Springboot+websocket+stomp实现消息功能
---

------

![](https://images.unsplash.com/photo-1556909172-89cf0b24ff02?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1051&q=80)

<!-- more -->

<center>

​	<span id="m_name"></span>

​	<audio id="m_url_id" controls> <source src=""> <source src="horse.ogg" type="audio/ogg"> Your browser does not support this audio format. 

​	</audio> 

</center>  

------
# 简述

在日常的web开发中，我们常用的就是http协议，这种协议是基于请求/响应的方式，由客户端发起请求，服务器端给出响应。现websocket协议是基于TCP协议开发的，在建立连接后，可以实现服务器端主动给客户端发送消息，以此来实现消息推送，聊天室等功能。

demo：https://github.com/LiJinHongPassion/springboot/tree/master/springboot_websocket_demo

# 一、websocket协议概述

  `Websocket protocol`是`HTML5`一种新的协议。它实现了浏览器与服务器全双工通信`full-duplex`)。**一开始的握手需要借助`HTTP`请求完成，后面的消息传输是基于长连接socket**。
    
  `WebSocket`是真正实现了全双工通信的服务器向客户端推的互联网技术。它是**一种在单个TCP连接上进行全双工通讯协议**。`Websocket`通信协议与2011年倍JETF定为标准`RFC 6455`， `Websocket API`被`W3C`定为标准。

------
全双工和单工的区别:	
- 全双工(`FullDuplex`)是通讯传输的一个术语。通信允许数据在两个方向上同时传输，它在能力上相当于两个单工通信方式的结合。全双工指可以同时(瞬时)进行信号的双向传输(A→B且B→A)。指A→B的同时B→A，**是瞬时同步的**。
- 单工、半双工(`Half Duplex`),所谓半双工就是指一个时间段内只有一个动作发生，举个简单例子，一条窄窄的马路，同时只能有一辆车通过，当目前有两辆车对开，这种情况下就只能一辆先过，等到头儿后另一辆再开，这个例子就形象的说明了半双工的原理。早期的对讲机、以及早期集线器等设备都是基于半双工的产品。随着技术的不断进步，半双工会逐渐退出历史舞台。

------
推的技术和拉的技术:
- 推送(`PUSH`)技术是一种建立在客户服务器上的机制，就是由服务器主动将信息发往客户端的技术。就像是广播电台播音。
- 同传统的拉(`PULL`)技术相比，最主要的区别在于推送(`PUSH`)技术是由**服务器主动向客户机发送信息**，而拉(`PULL`)技术则是**由客户机主动请求信息**。PUSH 技术的优势在于信息的主动性和及时性。

  简单的说，相对于服务端:拉的技术是被动向客户端提供数据，推的技术是主动向客户端提供数据。

------
# 二、websocket协议的优越性
- 传统的`http`协议实现方式：`http`协议可以多次请求，因为每次请求之后，都会关闭链接，下次重新请求数据，需要再次打开链接。方式有轮询（频繁请求）。

- 传统`socke`t技术：长连接（资源浪费）

- `websocket`协议实现方式：它是一种长连接，只能通过依次请求来初始化链接，然后所有的请求和响应都是用过这个**TCP链接**进行通讯，这意味着他是一种基于事件驱动，异步的消息机制。



** 问题：**

`Html5 websocket`兼容性还不是很好，不是所有的浏览器都支持这些新的API，特别是在IE10以下。

但幸运的是现在绝大多数主流的浏览器都支持这些API,即使不支持的哪些旧的浏览器，也有解决方案。如:
- 为了处理不同浏览器和浏览器版本的兼容性,`spring webscoket`基于`SockJs protecol`提供了一种解决兼容性的方法，在底层屏蔽兼容性的问题，提供统一的，透明的，可理解性的`webscoket`解决方案。。
- [SockJs](https://github.com/sockjs/sockjs-client)是一个浏览器上运行的`JavaScript`库,如果浏览器不支持`Websocket`, 该库可以模拟对`Websocket`的支持，实现浏览器和Web服务器之间低延迟、全双工、跨域的通讯通道。



# 三、springboot+websocket+stomp例子

先要明白WebSocket 是发送和接收消息的 底层API，而SockJS 是在 WebSocket 之上的 API；最后 STOMP（面向消息的简单文本协议）是基于 SockJS 的高级API

下面是我在CSDN上看到的一篇十分详细的blog，分别对websocket，sockjs，stomp进行了解释，分享出来大家一起学习。

**主要关注stomp部分的内容**

{% asset_img  1.png 1.png %}

