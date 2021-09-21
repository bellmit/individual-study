<template>
  <div>
    <el-row class="manage-btns-list">
      <el-col :span="2">
        <el-button v-if="btnsGroup.isAdd" type="primary" icon="el-icon-plus" @click="doOperation('add')">添加</el-button>
      </el-col>
      <el-col :span="2">
        <el-button v-if="btnsGroup.isEdit" type="success" icon="el-icon-circle-check" @click="doOperation('edit')">编辑</el-button>
      </el-col>
      
      <el-col :span="8" :offset="12">
        <el-input placeholder="请输入内容" v-model="content" class="input-with-select">
          <el-select v-model="label" slot="prepend" placeholder="请选择">
            <el-option label="餐厅名" value="1"></el-option>
            <el-option label="订单号" value="2"></el-option>
            <el-option label="用户电话" value="3"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search"></el-button>
        </el-input>
      </el-col>
    </el-row>
  </div>
</template>
<script>

  export default {
    name: "ManageBtns",
    props: ['type', 'btnsGroup'],
    data() {
      return {
        content: "",
        label: "",
        currentOp: "" // 当前操作类型（新增、启用、禁用）
      }
    },
    methods: {
      doOperation(opType) {
        this.currentOp = opType;
        this.$emit('change-data', opType);
      },
    },
    updated() {
    }
  }
</script>
<style scoped lang="scss">
  .manage-btns-list {
    padding-bottom: 20px;
  }
</style>