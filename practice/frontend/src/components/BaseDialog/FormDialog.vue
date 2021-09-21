<template>
  <!-- dialog -->
  <el-dialog :title="dialogTitle" :visible.sync="isVisible" :before-close="closeDialog">
    <el-form :model="form">
      <div v-for="(item, idx) in fields">
        <el-form-item :label="item.label" label-width="80px" :key="idx">
          <el-col v-if="item.type == 'date'" :span="10">
            <el-date-picker type="date" placeholder="选择日期" v-model="form[item.prop]" value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%;"></el-date-picker>
          </el-col>
          <el-select v-else-if="item.type=='select'" v-model="form[item.prop]" placeholder="请选择">
            <el-option v-for="it in item['selectOptions']" :key="it.value" :label="it.label" :value="it.value">
            </el-option>
          </el-select>
          <el-switch v-else-if="item.type == 'switch'" v-model="form[item.prop]" active-value="1" inactive-value="0">
          </el-switch>
          <el-input v-else-if="item.type == 'textarea'" type="textarea" :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入内容" v-model="form[item.prop]" class="input-item">
          </el-input>
          <el-upload ref="upload" v-else-if="item.type == 'file'" class="upload-demo"
            action="/file/upload" :on-preview="handlePreview" :on-remove="handleRemove"
            :before-remove="beforeRemove" multiple :limit="1" :on-exceed="handleExceed" :data="extData">
            <el-button size="small" type="primary">点击上传</el-button>
            <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
          </el-upload>
          <el-input v-else v-model="form[item.prop]" autocomplete="off" type="item.type ? item.type : 'text'"
            class="input-item"></el-input>
        </el-form-item>
      </div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="closeDialog">取 消</el-button>
      <el-button type="primary" @click="confirm">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
  export default {
    name: "FormDialog",
    props: [
      'dialogVisible',
      'dialogTitle',
      'fields',
      'form' // 表单数据
    ],
    data() {
      return {
        // form: {},
        extData: {
          userId: this.$store.getters.currentUser.uid,
        }
      }
    },
    computed: {
      isVisible: function () {
        return this.dialogVisible;
      },
      /* form: function () {
        let formObj = {};
        for (let i = 0, len = this.fields.length; i < len; i++) {
          let field = this.fields[i];
          if (field.isEdit && field.value) {
            console.log("render form object")
            formObj[field.prop] = "";
          }
        }
        return formObj;
      } */
    },
    methods: {
      closeDialog: function () {
        this.$emit('form-dialog-close');  // 子组件->父组件
      },
      confirm: function () {
        console.log("confirm form :", this.form)
        this.$emit("operation-confirm", this.form);
        this.resetForm();
      },
      resetForm: function () {
        this.form = {};
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      }
    },
    mounted() {
      
    }
  }

</script>
<style scoped>
  .el-form .input-item {
    width: 80%;
  }
</style>