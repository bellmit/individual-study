<template>
  <el-form ref="form" :model="form" label-width="120px">
    <el-form-item v-for="(it,idx) in formItems" :label="it.label" :key="idx">
      <div v-if="it.formType == 'textarea'">
        <el-input type="textarea" v-model="form[it.prop]" @input="onInput"/>
      </div>
      <div v-else>
        <el-input type="text" v-model="form[it.prop]" @input="onInput"/>
      </div>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSubmit">提交</el-button>
      <el-button @click="onCancel">取消</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
  import entities from '@/assets/javascript/entities.js'

export default {
  name: "CommonForm",
  props: ['formType'],
  data() {
    return {
      form: {
        name: ""
      },
      formItems: []
    }
  },
  methods: {
    onSubmit() {
      this.$emit('submit-form', this.form);
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    },
    onInput(e) {
      this.$forceUpdate();
    }
  },
  created() {
    console.log("form type >>" + this.formType);
    entities[this.formType].forEach((item, index) => {
      this.form[item.prop] = "";
      if (item.hasOwnProperty('isShow') && !item.isShow) {
        // ignore item
      } else {
        this.formItems.push(item);
      }
    });
    console.log(this.form)
  }
}
</script>
<style lang="scss" scoped>

</style>