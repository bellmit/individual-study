<template>
  <div>
    <el-card class="register-card">
    <el-row>
      <div class="title-container">注册</div>
    </el-row>
    <el-row>
      <el-col>
        <el-form :model="registryForm" :rules="rules" ref="registryForm" label-width="125px" class="registry-form">
          <el-form-item label="帐号：">
            <el-input v-model="registryForm.userName" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="姓名：">
            <el-input v-model="registryForm.userNameCn" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="邮箱：">
            <el-input v-model="registryForm.email" style="width: 250px"></el-input>
          </el-form-item>
          <el-form-item label="密码：">
            <el-input v-model="registryForm.password"  type="password" style="width: 250px"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('registryForm')">注册</el-button>
            <el-button type="primary" @click="resetForm('registryForm')">清空</el-button>
            <el-button type="text">
              <router-link to="/login">已有账号，马上登陆</router-link>
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
  </div>
</template>

<script>
  import apiTypes from '@/api/base/api-types'
  import {sysUserApi} from "@/api/sysUser";

  export default {
    name: "Register",
    data() {
      return {
        registryForm: {
          userName: "",
          userNameCn: "",
          password: "",
          secPassword: "",
          age: ""
        },
        rules: {
          userName: [
            { required: true, message: "请输入用户名", trigger: "blur" },
            { min: 3, max: 8, message: "长度在 3 到 8 个字符", trigger: "blur" }
          ],
          region: [{ required: true, message: "请输入秘密", trigger: "blur" }],
          password: [{ required: true }],
          secPassword: [{ required: true }]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            adminApi.offer(this.registryForm).then(resp => {
              this.$router.push("/login");
            });
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    }
  };
</script>

<style lang="scss" scoped>
  .register-card {
    width: 50%;
    margin: 0 auto;
  }
  .title-container {
    color: black;
    text-align: center;
    font-size: 1.5em;
    font-weight: bold;
    line-height: 2em;
  }
</style>