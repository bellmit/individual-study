<template>
  <el-container>
    <el-container>
      <el-aside width="200px">
        <div>
          当前用户ID: {{userId}}
        </div>
        <el-divider></el-divider>
        <span>在线用户 <i class="el-icon-refresh" @click="showOnlineUsers"></i></span>
        <ul>
          <li v-for='item in onlineUserIds'><a @click.prevent='doChat(item)'>{{item}}</a></li>
        </ul>
      </el-aside>
      <el-container class="chat-box">
        <el-main>
          <div class="chat-title">
            {{toUserId}}
          </div>
          <el-divider></el-divider>
          <div v-for='(item, idx) in chatDatas' :key='idx'>
            <el-row>
              <div style="text-align:center;padding: 15px 0;">
                <el-tag type="info" size="mini">{{item.sendDateTime}}</el-tag>
              </div>
            </el-row>
            <el-row>
              <el-col :span="2">
                <el-avatar></el-avatar>
              </el-col>
              <el-col :span="12">
                <div>
                  <span style="line-height: 23px;">{{item.userId}}：</span><br/>
                  <span>{{item.content}}</span>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-main>
        <el-footer>
          <el-form :inline="true" :model="chatData">
            <el-form-item>
              <el-input class="msg-input" v-model="chatData.sendMsg" placeholder="消息内容"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="doSendMsg">发送</el-button>
            </el-form-item>
          </el-form>
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>

  import * as ChatRequest from "@/api/chat.js";

  function randomCode(len) {
    let c = '';
    let fn = function () {
      let n = Math.floor(Math.random() * 10);
      return n;
    }
    while (c.length < len) {
      c += fn();
    }
    return c;
  }

  export default {
    name: "ChatRoom",
    data() {
      return {
        websocket: null,
        baseUrl: "ws://localhost:8083/im/websocket/",
        chatData: {
          sendMsg: "",
        },
        isShow: false,
        userId: '',
        toUserId: '',
        onlineUserIds: [],
        chatDatas: [
        ],
        his: {
          offlineMsgCnt: 0,
        }
      }
    },
    methods: {
      showChatRoom() {
        this.isShow = !this.isShow;
      },
      doSendMsg() {
        var msg = {
          "userId": this.userId,
          "toUserId": this.toUserId,
          "content": this.chatData.sendMsg
        }
        // 0 - 表示连接尚未建立，1 - 表示连接已建立，可以进行通信，
        // 2 - 表示连接正在进行关闭，3 - 表示连接已经关闭或者连接不能打开
        if (this.websocket.readyState == 1) {
          this.websocket.send(JSON.stringify(msg));
          this.chatData.sendMsg = "";
        } else {
          this.$message("消息发送失败..");
        }
      },
      showOnlineUsers() {
        ChatRequest.getOnlineUsers().then((resp) => {
          this.onlineUserIds = resp.data.filter(uid => uid != this.userId);
        })
      },
      handleMessage(msgObject) {
        if (msgObject.messageType == '10001') { // 普通消息展示
          this.chatDatas.push(msgObject);
        }
      },
      doChat(toUser) {
        this.toUserId = toUser;
        this.doConnectWs();
      },
      doConnectWs() {
        this.chatDatas = [];
        let that = this;
        return new Promise((resolve, reject) => {
          if ('WebSocket' in window) {
            var wsUrl = this.baseUrl + this.userId + "/" + this.toUserId;
            this.websocket = new WebSocket(wsUrl);
            this.websocket.onopen = this.onOpen;
            this.websocket.onclose = this.onClose;
            this.websocket.onmessage = this.onMessage;
            this.websocket.onerror = this.onError;
            this.$message('正在连接...');
            resolve();
          } else {
            this.$message('当前浏览器不支持 WebSocket');
            reject();
          }
        });
      },
      onOpen() {
        this.$message({
          showClose: true,
          message: '连接成功',
          type: 'success'
        });
      },
      onMessage(evt) {
        var respData = evt.data;
        console.log("OnMessage :" + respData);
        this.handleMessage(JSON.parse(respData));
      },
      onClose() {
        this.$message({
          showClose: true,
          message: '连接断开'
        });
      },
      onError() {
        this.$message({
          showClose: true,
          message: '连接异常',
          type: 'error'
        });
      }
    },
    created() {
      this.userId = this.$store.getters.name;
      console.log("this user id :", this.userId);
    },
    mounted() {
      ChatRequest.getOfflineCount(this.userId).then(resp => {
        this.his.offlineMsgCnt = resp.data;
      });
      this.showOnlineUsers(); // 查询在线用户列表
    },
    destory() {
      console.log("destory , close websocket for ", this.userId)
      if (this.websocket) {
        this.websocket.close();
      }
    }
  }

</script>

<style lang="scss" scoped>
  .el-aside,
  .el-main {
    height: 80vh;
  }

  .input-position {
    bottom: 20px;
  }

  .msg-input {
    width: 340px;
  }

  .chat-box {
    border-left: 1px black solid;
  }

  .chat-title {
    text-align: center;
  }
</style>