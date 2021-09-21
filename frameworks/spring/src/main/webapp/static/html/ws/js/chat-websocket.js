var websocket = null;
var fromUserId = null;
var wsUrl = "ws://localhost:8080/gtools/chat/";

$(function () {

    bindEvents();
    /* var vm = new Vue({
        el: "#chat",
        data: data,
        methods: {
            doConnect: doConnect,
            doSendMsg: doSendMsg,
            doClose: doClose
        },
        before: {
            bindEvents
        }
    }); */
});

function doSendMsg() {
    var content = $("#msgContent").val();
    var toUserId = $("#toUserId").val();
    var sendMsg = {
        "fromUserId": fromUserId,
        "toUserId": toUserId,
        "content": content
    }
    websocket.send(JSON.stringify(sendMsg));
}

function doConnect() {
    fromUserId = $("#fromUserId").val();
    console.log("userId:" + fromUserId);
    if ("WebSocket" in window) {
        var ws = wsUrl + fromUserId;
        websocket = new WebSocket(ws);
        websocket.onopen = onConnected;
        websocket.onclose = onClosed;
        websocket.onmessage = onMessage;
        websocket.onerror = onError;
    } else {
        showMessage("连接失败...")
    }
}

function doClose() {
    websocket.close();
}

function bindEvents() {
    $("button[name='wsBtn']").click(function () {
        var operType = $(this).val();
        // console.log(operType);
        switch (operType) {
            case "1":
                doConnect();
                break;
            case "2":
                doSendMsg();
                break;
            case "3":
                doClose();
                break;
            default:
                console.error("not support>" + operType);
        }
    });
}

window.onclose = doClose;

function showMessage(msg) {
    $(".content").append(msg + "<br>");
}

function onMessage(evt) {
    showMessage(evt.data);
}

function onConnected() {
    showMessage("连接成功");
}

function onClosed() {
    showMessage("连接关闭");
}

function onError() {
    showMessage("连接异常");
}