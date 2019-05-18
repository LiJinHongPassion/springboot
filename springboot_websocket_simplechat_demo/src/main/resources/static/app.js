var stompClient = null;
var username = "";

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

function connect() {

    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.subscribe('/topic/groupchat', function (message) {
            showGroupChat(JSON.parse(message.body).name + ": " + JSON.parse(message.body).inner);
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
function login() {
    username = $("#name").val();
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}
function sendInner() {
    stompClient.send("/app/groupchat", {}, JSON.stringify({'name':username,'inner': $("#inner").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}
function showGroupChat(message) {
    $("#chatInner").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#login" ).click(function() { login(); });
    $( "#send" ).click(function() { sendInner(); });
});