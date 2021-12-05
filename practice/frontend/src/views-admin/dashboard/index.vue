<template>
  <div class="app-container">
    <el-collapse v-model="activeName" accordion>
      <el-collapse-item title="小区排行榜" name="1">
        <el-row>
          <el-col :span="12">
            <span class="demonstration">日期：</span>
            <el-date-picker
              v-model="pickerDate"
              align="right"
              type="date"
              placeholder="选择日期"
              format="yyyy-MM-dd"
              value-format="yyyyMMdd"
              :picker-options="datePickerOptions"
            >
            </el-date-picker>
          </el-col>
          <el-col :span="12">
            <label>区域：</label>
            <el-cascader
              placeholder="选择省-市-区"
              :options="districtOptions"
              collapse-tags
              clearable
              @change="changeDistrict"
            ></el-cascader>
          </el-col>
        </el-row>
        <el-row>
          <div
            id="disRankBarChart"
            :style="{ width: '100%', height: '500px' }"
          />
        </el-row>
      </el-collapse-item>
      <el-collapse-item title="小区最高/最低价格统计" name="2">
        <el-row>
          <el-col :span="12">
            <span class="demonstration">日期：</span>
            <el-date-picker
              v-model="pickerDate"
              align="right"
              type="date"
              placeholder="选择日期"
              format="yyyy-MM-dd"
              value-format="yyyyMMdd"
              :picker-options="datePickerOptions"
            >
            </el-date-picker>
          </el-col>
          <el-col :span="12">
            <label>市：</label>
            <el-cascader
              placeholder="可选任意一级"
              :options="cityOptions"
              :props="{ checkStrictly: true }"
              clearable
              @change="changeCity"
            ></el-cascader>
          </el-col>
        </el-row>
        <el-row>
          <div
            id="minMaxOfCityBarChart"
            :style="{ width: '100%', height: '500px' }"
          />
        </el-row>
      </el-collapse-item>
      <el-collapse-item title="小区价格趋势图" name="3">
        <el-row>
          <el-col :span="12">
            <label>小区：</label>
            <el-select v-model="selectVillage" filterable placeholder="请选择">
              <el-option
                v-for="item in villageOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-col>
        </el-row>
        <el-row>
          <div
            id="villageTrendBarChart"
            :style="{ width: '100%', height: '500px' }"
          />
        </el-row>
      </el-collapse-item>
    </el-collapse>
    <el-row>
      <div id="pieChart" :style="{ width: '600px', height: '500px' }" />
    </el-row>
  </div>
</template>

<script>
import { Message } from 'element-ui'

import LoggerFactory from '@/api/base/logger'
import { houseInfoApi } from '@/api/res/houseInfo'

let logger = LoggerFactory.getLogger('Dashboard')

export default {
  name: 'Dashboard',
  data() {
    return {
      // 日期选择
      pickerDate: '',
      datePickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        },
        shortcuts: [
          {
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date())
            },
          },
          {
            text: '昨天',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', date)
            },
          },
        ],
      },
      activeName: '',
      // 图
      disRankBarChart: null,
      minMaxOfCityBarChart: null,
      villageTrendBarChart: null,

      // 省市区-级联选择器
      districtOptions: [],
      // 市级选择器
      cityOptions: [],
      // 小区Select选择器
      villageOptions: [],

      // 小区Rank数据
      villageRank: [],
      minAndMaxVillageList: [],
      selectVillage: '',
    }
  },
  created() {},
  mounted() {
    this.$nextTick(() => {
      logger.info(window.innerWidth + 'px')
      let ele = document.getElementById('disRankBarChart')
      ele.style.width = window.innerWidth - 100 + 'px'
      // 获取元素
      this.disRankBarChart = this.$echarts.init(
        this.resetChartWidth('disRankBarChart')
      )
      this.minMaxOfCityBarChart = this.$echarts.init(
        this.resetChartWidth('minMaxOfCityBarChart')
      )
      this.villageTrendBarChart = this.$echarts.init(
        this.resetChartWidth('villageTrendBarChart')
      )
      // 请求数据，获取查询条件
      houseInfoApi.getCondition().then((resp) => {
        this.initQueryCondition(resp.data)
      })
    })

    this.drawPie()
  },
  methods: {
    // 修改市
    changeCity(value) {
      if (!this.checkParams()) {
        return
      }
      logger.info('changeCity 条件：' + value + ',' + this.pickerDate)
      if (value && value.length > 0) {
        // minAndMaxVillageList
        houseInfoApi
          .getMinMaxOfCity({
            crawlDate: this.pickerDate,
            province: value[0],
            city: value[1],
          })
          .then((response) => {
            logger.info('getMinMaxOfCity size:' + response.data.length)
            this.minAndMaxVillageList = response.data
            this.drawMinMaxOfCityBar()
          })
      }
    },

    // 修改区域
    changeDistrict(value) {
      if (!this.checkParams()) {
        return
      }
      logger.info('changeDistrict 条件：' + value + ',' + this.pickerDate)
      if (value && value.length > 0) {
        houseInfoApi
          .getVillageRank({
            crawlDate: this.pickerDate,
            province: value[0],
            city: value[1],
            district: value[2],
          })
          .then((response) => {
            this.villageRank = response.data.villageList
            logger.info('小区个数:' + this.villageRank.length)
            this.drawDisRankBar(response.data.crawlDate)
          })
      }
    },

    initQueryCondition(data) {
      let conditionList = data
      this.cascaderPropAdapter(conditionList)
      this.districtOptions = conditionList
      this.cityOptions = conditionList
    },

    // 将返回值列表转换成el-cascader的属性
    cascaderPropAdapter(arr) {
      let that = this
      for (let i = 0, len = arr.length; i < len; i++) {
        arr[i].label = arr[i].value
        if (arr[i].children && arr[i].children.length > 0) {
          that.cascaderPropAdapter(arr[i].children)
        } else {
          arr[i].children = null
          // 构建小区查询条件
          this.villageOptions.push({ label: arr[i].value, value: arr[i].key })
        }
      }
    },

    // 请求参数校验
    checkParams() {
      if (!this.pickerDate) {
        Message({
          message: '请选择日期',
          type: 'error',
          duration: 1 * 1000,
        })
        return false
      }
      return true
    },

    drawDisRankBar(crawlDate) {
      let options = {
        // 图表的标题
        title: {
          text: '最新小区排行榜 ' + crawlDate,
          top: '10px',
          left: '10px',
        },
        // 图例组件，可以展示不同系列的标记，颜色及名字，点击图例控制哪些系列不展示
        legend: {
          type: 'plain',
          // 避免图例和标题重叠
          top: '30px',
        },
        // 提示框组件
        tooltip: {},
        dataset: {
          dimensions: ['name', 'price'],
          source: this.villageRank,
        },
        // 避免x轴显示不全
        grid: {
          y2: 140,
          // 组件与左边的距离
          left: '12%',
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            // 横轴信息全部显示
            interval: 0,
            // -30度角倾斜
            rotate: -30,
          },
        },
        yAxis: {},
        // Declare several bar series, each will be mapped
        // to a column of dataset.source by default.
        series: [{ type: 'bar', barMaxWidth: 50 }],
      }
      this.disRankBarChart.setOption(options)
    },

    drawMinMaxOfCityBar() {
      let options = {
        // 图表的标题
        title: {
          text: '区域最高、最低价格',
          top: '10px',
          left: '10px',
        },
        // 图例组件，可以展示不同系列的标记，颜色及名字，点击图例控制哪些系列不展示
        legend: {
          type: 'plain',
          // 避免图例和标题重叠
          top: '30px',
          data: ['minPrice', 'maxPrice'],
        },
        // 提示框组件
        tooltip: {},
        dataset: {
          dimensions: ['district', 'minPrice', 'maxPrice'],
          source: this.minAndMaxVillageList,
        },
        // 避免x轴显示不全
        grid: {
          y2: 140,
          // 组件与左边的距离
          left: '12%',
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            // 横轴信息全部显示
            interval: 0,
            // -30度角倾斜
            rotate: -30,
          },
        },
        yAxis: {},
        // Declare several bar series, each will be mapped
        // to a column of dataset.source by default.
        series: [
          { type: 'bar', barMaxWidth: 50 },
          { type: 'bar', barMaxWidth: 50 },
        ],
      }
      this.minMaxOfCityBarChart.setOption(options)
    },

    drawVillageTrendBar() {
      this.villageTrendBarChart.setOption(options)
    },

    drawPie() {
      const pieChart = this.$echarts.init(document.getElementById('pieChart'))
      // 绘制图表
      pieChart.setOption({
        series: [
          {
            type: 'pie',
            data: [
              {
                value: 335,
                name: '直接访问',
              },
              {
                value: 234,
                name: '联盟广告',
              },
              {
                value: 1548,
                name: '搜索引擎',
              },
            ],
          },
        ],
      })
    },

    resetChartWidth(domId) {
      let ele = document.getElementById(domId)
      ele.style.width = window.innerWidth - 100 + 'px'
      return ele
    },
  },
}
</script>

<style scoped>
.el-row {
  margin-top: 10px;
}
.el-cascader {
  width: 240px;
}
</style>
