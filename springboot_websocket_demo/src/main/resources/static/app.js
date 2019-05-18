var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

/**
 *
 * 该connect()函数使用SockJS和stomp.js打开与“/ gs-guide-websocket”的连接，
 * 这是我们的SockJS服务器等待连接的地方。在成功连接后，客户端订阅“/ topic / greetings”目的地，
 * 服务器将在该目的地发布问候消息。当在该目的地上收到问候语时，它将向DOM附加一个段落元素以显示问候语消息。
 *
 */
function connect() {
    /**
     * SockJS地址：https://github.com/sockjs/sockjs-client（含有方法介绍）
     * SockJS客户端的依赖 --- js文件
     * <dependency>
        <groupId>org.webjars</groupId>
         <artifactId>sockjs-client</artifactId>
         <version>1.0.2</version>
       </dependency>

     *  SockJS所处理的URL是http://或https://,不再是ws://和wss://
       使用相对URL。例如，如果包含JavaScript的页面位于"http://localhost:8080/websocket"的路径下
       那么给定的"gs-guide-websocket"路径将会形成到"http://localhost:8080/websocket/gs-guide-websocket"的连接
     */
    var socket = new SockJS('/gs-guide-websocket');
    /**
     * Stomp的依赖 --- js文件
     * <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>stomp-websocket</artifactId>
         <version>2.3.3</version>
       </dependency>
     */
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

/**
 *
 * 该sendName()函数检索用户输入的名称，
 * 并使用STOMP客户端将其发送到“/ app / hello”目的地（GreetingController.greeting()将在何处接收）。
 *
 */
function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});