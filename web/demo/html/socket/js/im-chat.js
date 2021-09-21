var websocket = null;
var baseUrl = "ws://127.0.0.1:18080";
// var baseUrl = "wss://10.1.6.53:18443";

var fromUserId = "";
var toUserId = "";
var role = "";
var rtmMessage = {
    uid: "",
    channelId: undefined
}
var heartBeatFn = null;

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
        for (var i = 0; i < params.length; i++) {
            var oneParam = params[i].split("=");
            theRequet[oneParam[0]] = oneParam[1];
        }
        console.log("params: " + JSON.stringify(theRequet));
    }
}

function bindEvents() {
    $("#connectWs").click(function () {
        fromUserId = $("input[name='fromUserId']").val();
        toUserId = $("input[name='toUserId']").val();
        role = $("#role option:selected").val();
        connectWsServer();
    });
    $("#closeWs").click(function () {
        if (websocket) {
            websocket.close();
        }
    });

    $("#sendMsg").click(function () {
        doSendMsg();
    });
    $("#broadcastMsg").click(function () {
        doBroadcastMsg();
    });

    $("#login").click(function () {
        getToken();
        doLogin();
    });
    $("#acceptCall").click(function () {
        acceptCall();
    });
    $("#rejectCall").click(function () {
        rejectCall();
    });
    $("#sendPing").click(function () {
        doSendPing();
    });
    $("#thirdInvite").click(function () {
        doThirdInvite();
    });
    $("#joinChannel").click(function () {
        joinChannel();
    })
    $("#leaveChannel").click(function () {
        leaveChannel();
    });
}

function connectWsServer() {
    if ('WebSocket' in window) {
        if (!fromUserId) {
            alert("请输入用户id")
            return;
        }
        var wsUrl = baseUrl + "/" + fromUserId + "/" + role;
        websocket = new WebSocket(wsUrl);
        websocket.onopen = onOpen;
        websocket.onclose = onClose;
        websocket.onmessage = onMessage;
        websocket.onerror = onError;
        showMessage("连接成功...");
        // heartBeat(); // 启动心跳检测
    } else {
        showMessage("当前浏览器不支持 WebSocket");
    }
}

function doSendMsg() {
    var content = $("input[name='msg']").val();
    var msg = {
        "messageType": "1300",
        "uid": $("input[name='fromUserId']").val(),
        "channelId": rtmMessage.channelId,
        "content": JSON.stringify({
            "toUid": $("input[name='toUserId']").val()
        }),
        "sendInfo": content
    }
    msg = "1234";
    if (websocket.readyState == websocket.OPEN) {
        websocket.send(JSON.stringify(msg));
    } else {
        showMessage("消息发送失败..")
    }
}

function doBroadcastMsg() {
    console.log("bd rtmMessage.channelId :" + rtmMessage.channelId)
    var content = $("input[name='msg']").val();
    var msg = {
        "messageType": "1400",
        "uid": $("input[name='fromUserId']").val(),
        "channelId": rtmMessage.channelId,
        "content": "",
        "sendInfo": JSON.stringify({
            "msgType": "Chatting",
            "msg": JSON.stringify({
                "userId": "dd1dd047-09d8-11eb-a096-080027b62bb6",
                "name": "客服01",
                "date": "2020-12-21 16:51:29",
                "content": "测试文本",
                "conversationId": "20201221165108048965dda43"
            }),
            "content": content
        })
    }
    if (websocket.readyState == websocket.OPEN) {
        websocket.send(JSON.stringify(msg));
    } else {
        showMessage("消息发送失败..")
    }
}

function doLogin() {
    var msg = {
        "messageType": 2000,
        "channelId": "",
        "content": JSON.stringify({
            "uid": $("input[name='fromUserId']").val()
        })
    }
    websocket.send(JSON.stringify(msg));
    showMessage("登录成功..");
}

function doSendPing() {
    var msg = {
        "messageType": "10000"
    }
    if (websocket.readyState == websocket.OPEN) {
        websocket.send(JSON.stringify(msg));
    }
}

function acceptCall() {
    var msg = {
        "messageType": 1010,
        "uid": $("input[name='fromUserId']").val(),
        "role": $("#role option:selected").val(),
        "channelId": rtmMessage.channelId,
        "content": JSON.stringify({
            "result": "0"
        })
    }
    websocket.send(JSON.stringify(msg));
}

function rejectCall() {
    var msg = {
        "messageType": 1010,
        "uid": $("input[name='fromUserId']").val(),
        "role": $("#role option:selected").val(),
        "channelId": rtmMessage.channelId,
        "content": JSON.stringify({
            "csrId": $("input[name='fromUserId']").val(),
            "result": "1"
        })
    }
    websocket.send(JSON.stringify(msg));
}

function doThirdInvite() {
    var msg = {
        "messageType": 1500,
        "uid": $("input[name='fromUserId']").val(),
        "role": $("#role option:selected").val(),
        "channelId": rtmMessage.channelId,
        "content": JSON.stringify({
            "toUid": $("input[name='toUserId']").val()
        })
    }
    websocket.send(JSON.stringify(msg));
}

function joinChannel() {
    let channelId = rtmMessage.channelId;
    if (channelId == null || channelId == undefined) {
        rtmMessage.channelId = "987650";
    }
    var msg = {
        "messageType": 1600,
        "uid": $("input[name='fromUserId']").val(),
        "role": $("#role option:selected").val(),
        "channelId": rtmMessage.channelId,
        "content": ""
    }
    websocket.send(JSON.stringify(msg));
    showMessage(msg.uid + "加入，房间号：" + rtmMessage.channelId);
}

function leaveChannel() {
    var msg = {
        "messageType": 1700,
        "uid": $("input[name='fromUserId']").val(),
        "role": $("#role option:selected").val(),
        "channelId": rtmMessage.channelId,
        "content": ""
    }
    websocket.send(JSON.stringify(msg));
    showMessage(msg.uid + "离开，房间号：" + rtmMessage.channelId);
}

function heartBeat() {
    var msg = {
        "messageType": 10000
    }
    heartBeatFn = setInterval(function () {
        websocket.send(JSON.stringify(msg));
        console.log("发送心跳包!" + new Date())
    }, 60 * 1000);
}

function onOpen() {
    console.log("Websocket connect success..")
}

function onMessage(evt) {
    var respData = JSON.parse(evt.data);
    console.log(respData);

    var mesType = respData.messageType;
    console.log(mesType)
    if (mesType == 1000) { // invite call
        rtmMessage.channelId = respData.channelId;
        var contentObj = JSON.parse(respData.content);
        rtmMessage.customerId = respData.uid;
        showMessage("来自[" + rtmMessage.customerId + "]通话，房间号：" + rtmMessage.channelId);
    }
    if (mesType == 1020) { // invite result
        rtmMessage.channelId = respData.channelId;
        showMessage("客服通话接收，房间号：" + respData.channelId);
    }
    if (mesType == 1100) { // cancel call
        var contentObj = JSON.parse(respData.content);
        showMessage("用户[" + respData.uid + "]取消了通话");
    }
    if (mesType == 1310) { // p2p
        var contentObj = JSON.parse(respData.content);
        showMessage("用户[" + respData.uid + "]说：" + respData.sendInfo);
    }
    if (mesType == 1200) { // end call
        var contentObj = JSON.parse(respData.content);
        showMessage("用户[" + respData.uid + "]结束了通话。");
    }
}

function onClose() {
    showMessage("连接关闭");
    clearInterval(heartBeatFn);
}

function onError() {

}

function showMessage(content) {
    $("#msgDisplay").append(content + "<br>");
}

window.close = function () {
    if (websocket) {
        websocket.close();
    }
}

function getToken() {
    var url = "http://localhost:18080/getToken";
    var data = {
        "customerId": $("input[name='fromUserId']").val(),
        "role": $("#role option:selected").val()
    }
    $.ajax({
        type: 'POST',
        url: url,
        async: false,
        data: JSON.stringify(data),
        contentType: "application/json;charset=utf-8",
        success: function (respData) {
            console.log(respData)
        }
    });
}

function doEndcall() {
    var url = "http://localhost:18080/endCall";
    var data = {
        "customerId": $("input[name='fromUserId']").val(),
        "role": $("#role option:selected").val()
    }
    $.ajax({
        type: 'POST',
        url: url,
        async: false,
        data: JSON.stringify(data),
        contentType: "application/json;charset=utf-8",
        success: function (respData) {
            console.log(respData)
        }
    });
}