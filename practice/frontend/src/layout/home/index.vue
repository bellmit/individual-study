<template>
  <div class="app-wrapper">
    <el-container>
      <el-header>
        <home-header></home-header>
      </el-header>
      <el-main>
        <home-body></home-body>
      </el-main>
      <hr />
      <el-footer>
        <home-footer></home-footer>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import { HomeHeader, HomeBody, HomeFooter } from "./components";

export default {
  name: "Home",
  data() {
    return {};
  },
  components: {
    HomeHeader,
    HomeBody,
    HomeFooter
  }
};
</script>

<style lang="scss" scoped>
@import "~@/styles/variables.scss";

.app-wrapper {
  font-size: 17px;
  color: #6f6f6f;
}
.el-header {
   /* background-color: #b3c0d1; */
  color: #333;
  text-align: center;
  line-height: 60px;
}
</style>