<template>
  <div class="user-info">
    <el-row>
      <el-col :span="2">账号</el-col>
      <el-col :span="10">
        <el-input v-model="userForm.name"></el-input>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="2">头像</el-col>
      <el-col :span="10">
        <el-upload class="avatar-uploader" action="/file/upload" :show-file-list="false"
          :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-button type="primary">
          <router-link to="/">返回首页</router-link>
        </el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import apiTypes from '@/api/base/api-types'
  import fileStore from '@/api/fileStore.js';

  export default {
    name: "UserInfo",
    data() {
      return {
        imageUrl: '',
        userForm: {

        }
      };
    },
    methods: {
      handleAvatarSuccess(resp, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
        this.$store.commit('user/SET_AVATAR', this.imageUrl);
        this.BASE_APIS.patch(apiTypes.USER, {
          uid: this.$store.getters.currentUser.uid,
          avatar: resp.data
        }).then(resp => {
          this.$message({
            message: '上传成功',
            type: 'success'
          });
        })
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      }
    },
    mounted() {
      this.imageUrl = this.$store.getters.avatar;
      this.userForm.name = this.$store.getters.currentUser.userName;
    }
  }
</script>

<style lang="scss" scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 50px;
    height: 50px;
    line-height: 60px;
    text-align: center;
  }

  .avatar {
    width: 50px;
    height: 50px;
    display: block;
  }

  .user-info {
    margin: auto;
  }

  .el-row {
    margin-bottom: 1.5em;
  }
</style>