<template>
  <div>
    <manage-btns type="user-mng" :btnsGroup="btnsGroup" @change-data="doUpdate"></manage-btns>

    <el-table ref="dataTable" @select="dataSelect" :data="tableDatas" border style="width: 100%" height="720">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column v-for="(data, idx) in showFields" :prop="data.prop" :label="data.label" :width="data.width"
        :key="idx" />
      <el-table-column v-if="enableOperation" fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="danger" size="small" @click.prevent="doDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageInfo.page"
        :page-sizes="[pageInfo.limit, pageInfo.limit * 2]" :page-size="pageInfo.limit" layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.total"
        class="fr">
      </el-pagination>
    </div>

    <!-- dialog -->
    <FormDialog dialogTitle="添加" :dialogVisible="showAdd" :fields="addFields" :form="formData" @operation-confirm="doAdd" @form-dialog-close="closeDialog"></FormDialog>
    <FormDialog dialogTitle="编辑" :dialogVisible="showEdit" :fields="editFields" :form="formData" @operation-confirm="doEdit" @form-dialog-close="closeDialog"></FormDialog>

  </div>
</template>

<script>
  import ManageBtns from '@/components/ManageBtns'
  import FormDialog from '@/components/BaseDialog/FormDialog.vue'
  import { MessageBox, Message } from 'element-ui'
  import apiTypes from '@/api/api-types'

  export default {
    name: "TableView",
    props: ['dataType'],
    components: {
      ManageBtns,
      FormDialog
    },
    data() {
      return {
        currentSelectd: -1,

        showAdd: false,
        showEdit: false,
        showFields:[],
        addFields:[],
        editFields: [],
        formData: {}, // 新增或编辑的表单对象

        // 按钮组件
        btnsGroup: { // 通用按钮组件是否显示控制
          isAdd: false,
          isEdit: false
        },
        enableOperation: true // 是否显示操作列
      }
    },
    props: {
      tableDatas: {
        type: Array,
        default: []
      },
      columns: {
        type: Array
      },
      pageInfo: {
        type: Object
      },
    },
    methods: {
      doUpdate(param) {
        if (param == 'add') {
          this.reset();
          this.showAdd = true;
        } else if (param == 'edit') {
          if (this.currentSelectd == -1) {
            Message({
              message: '请选择一条数据',
              type: 'warn',
              duration: 5 * 1000
            })
          } else {
            this.showEdit = true;
          }
        }
      },
      // selection: 选中的所有行，row：当前选中的行；
      dataSelect(selection, row) {
        if (selection.length == 0) {
          this.currentSelectd = -1;
        } else {
          this.currentSelectd = row.uid;
          this.formData = Object.assign({}, row);
        }
      },
      reset() {
        this.formData = {};
        // 取消所有行选中状态
        this.$refs.dataTable.clearSelection();
        this.currentSelectd == -1
      },
      closeDialog() {
        this.showEdit = false;
        this.showAdd = false;
      },
      doAdd(data) {
        this.closeDialog();
        this.$emit("addData", data);
      },
      doEdit(data) {
        this.closeDialog();
        this.BASE_APIS.patch(this.targetApi, data).then(resp => {
          this.getDatas();
        });
      },
      doDelete(curIdx, curRow) {
        this.$emit("deleteRow", curRow);
      },
      handleSizeChange(val) {
        console.log("handleSizeChange")
        this.$emit("pageChange", {page: this.pageInfo.page, limit: val});
      },
      handleCurrentChange(val) {
        console.log("handleCurrentChange", val)
        console.log(this.pageInfo)
        this.$emit("pageChange", {page: val, limit: this.pageInfo.limit});
      }
    },
    created() {

    },
    mounted() {
      this.showFields = this.columns.filter(item => item.isShow);
      this.addFields = this.columns.filter(item => item.isAdd);
      this.editFields = this.columns.filter(item => item.isEdit);
      if (this.addFields && this.addFields.length > 0) {
        this.btnsGroup.isAdd = true;
      }
      if (this.editFields && this.editFields.length > 0) {
        this.btnsGroup.isEdit = true;
      }
    }
  }
</script>