<template>
  <div>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="">首页</el-menu-item>
      <!-- <el-menu-item index="news">今日大事</el-menu-item> -->
      <!-- <el-submenu index="2">
        <template slot="title">架构</template>
        <el-menu-item index="md/corejava">CoreJava</el-menu-item>
        <el-menu-item index="2-2">Redis</el-menu-item>
        <el-submenu index="2-4">
          <template slot="title">MQ</template>
          <el-menu-item index="2-4-1">ActiveMQ</el-menu-item>
          <el-menu-item index="2-4-2">RabbitMQ</el-menu-item>
          <el-menu-item index="2-4-3">RocketMQ</el-menu-item>
        </el-submenu>
      </el-submenu> -->
      <el-menu-item index="chat/room">聊天</el-menu-item>
      <el-menu-item index="shop">社区商店</el-menu-item>
      <el-menu-item index="md">学习手册</el-menu-item>
      <!--  -->
      <el-menu-item v-show="roles.indexOf('超级管理员') > -1" index="admin" class="right-float">
        <span>后台</span>
      </el-menu-item>
      <el-menu-item v-if="!name" index="register" class="right-float">
        <span>注册</span>
      </el-menu-item>
      <el-menu-item v-if="!name" index="login" class="right-float">
        <span>
          <router-link to="/login">登录</router-link>
        </span>
      </el-menu-item>
      <el-menu-item v-if="name" index="username" class="right-float">
        <!-- <span>{{ name }}</span> -->
        <el-dropdown class="avatar-container" trigger="click">
          <div class="avatar-wrapper">
            <img :src="avatarUrl" class="user-avatar">
            <i class="el-icon-caret-bottom caret-icon" />
          </div>
          <el-dropdown-menu slot="dropdown" class="user-dropdown">
            <el-dropdown-item>{{name}}</el-dropdown-item>
            <router-link to="/user">
              <el-dropdown-item>
                上传头像
              </el-dropdown-item>
            </router-link>
            <el-dropdown-item divided>
              <span style="display:block;" @click="logout">登出</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu-item>
      <el-menu-item index="post" class="right-float">
        <span>留言</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
  import { mapGetters } from "vuex";

  // TODO： 可以从表中或redis中取
  let menus = [
    { name: "post", titel: "帖子" }
  ]

  export default {
    name: "HomeHeader",
    components: {},
    data() {
      return {
        activeIndex: "",
        roles: this.$store.getters.roles,
        name: this.$store.getters.name,
        avatar: this.$store.getters.avatar,
        avatarUrl: ""
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        if ("username" == key) {
          return;
        }
        console.log("router to :" + key)
        this.$router.push("/" + key);
      },
      async logout() {
        await this.$store.dispatch('user/logout')
        this.$router.push(`/`)
      }
    },
    mounted() { 
      this.avatarUrl = this.FILE_SERVICE + "upload/" + this.avatar;
    }
  };
</script>

<style scoped lang="scss">
  .el-menu-demo {
    /* background-color: gray; */
  }
  .right-float {
    float: right;
  }

  .avatar-container {
    margin-right: 30px;

    .avatar-wrapper {
      margin-top: 5px;
      position: relative;

      .user-avatar {
        cursor: pointer;
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }

      .el-icon-caret-bottom {
        cursor: pointer;
        position: absolute;
        right: -30px;
        top: 30px;
        font-size: 12px;
      }
    }
  }
</style>