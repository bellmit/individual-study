var websocket = null;
var baseUrl = "ws://localhost:8083/im/";

var fromUserId = "";
var toUserId = "";

$(function () {
    initParams();
    bindEvents();
});

function initParams() {
    var url = window.location.search;
    var theRequet = {};
    if (url.indexOf("?") != -1) {
        var paramsStr = url.substr(1);
        var params = paramsStr.split("&");
        for (var i = 0 ; i < params.length ; i ++) {
            var oneParam = params[i].split("=");
            theRequet[oneParam[0]] = oneParam[1];
        }
        console.log("params: " + JSON.stringify(theRequet));
    }
}

function bindEvents() {
    $("#connectWs").click(function() {
        fromUserId = $("input[name='fromUserId']").val();
        toUserId = $("input[name='toUserId']").val();
        connectWsServer();
    });

    $("#sendMsg").click(function() {
        doSendMsg();
    });
}

function connectWsServer() {
    if ('WebSocket' in window) {
        var wsUrl = baseUrl + fromUserId + "/" + toUserId;
        websocket = new WebSocket(wsUrl);
        websocket.onopen = onOpen;
        websocket.onclose = onClose;
        websocket.onmessage = onMessage;
        websocket.onerror = onError;
        showMessage("连接成功...");
    } else {
        showMessage("当前浏览器不支持 WebSocket");
    }
}

function doSendMsg() {
    var content = $("input[name='msg']").val();
    var msg = {
        "userId": fromUserId,
        "toUserId": toUserId,
        "content": content
    }
    if (websocket.readyState == websocket.OPEN) {
        websocket.send(JSON.stringify(msg));
    } else {
        showMessage("消息发送失败..")
    }
}

function onOpen() {
    console.log("Websocket connect success..")
}

function onMessage(evt) {
    var respData = evt.data;
    console.log(respData);
    showMessage(respData);
}

function onClose() {
    showMessage("连接关闭");
}

function onError() {

}

function showMessage(content) {
    $("#msgDisplay").append(content + "<br>");
}

window.close = function() {
    if (websocket) {
        websocket.close();
    }
}