<template>
  <div class="app-container">
    <dl>
      <dt>1.同一天同一个区不同小区价格从高到底排名</dt>
      <dt>2.不同小区最高价格，最低价格柱状图</dt>
    </dl>
    <el-row>
      <el-cascader
        size="large"
        :props="props"
        :options="options"
        v-model="selectedOptions"
        @change="handleChange"
      >
      </el-cascader>
      <div id="barChart" :style="{ width: '800px', height: '500px' }" />
    </el-row>
    <el-row>
      <div id="pieChart" :style="{ width: '600px', height: '500px' }" />
    </el-row>
  </div>
</template>

<script>
import { houseInfoApi } from '@/api/res/houseInfo'

export default {
  name: 'Dashboard',
  data() {
    return {
      // 省市区级联选择器
      props: { multiple: true },
      options: [],
      selectedOptions: [],
      // 小区Rank数据
      villageRank: [],
    }
  },
  created() {},
  mounted() {
    houseInfoApi
      .getVillageRank({
        province: '安徽',
        city: '马鞍山市',
        district: '花山区',
      })
      .then((response) => {
        this.villageRank = response.data.villageList
        this.drawBar(response.data.crawlDate)
      })
    this.drawPie()
  },
  methods: {
    drawBar(crawlDate) {
      const barChart = this.$echarts.init(document.getElementById('barChart'))
      let options = {
        // 图表的标题
        title: {
          text: '小区排行榜 ' + crawlDate,
          top: '0px',
          left: '10px',
        },
        // 图例组件，可以展示不同系列的标记，颜色及名字，点击图例控制哪些系列不展示
        legend: {
          type: 'plain',
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
        series: [{ type: 'bar' }],
      }
      barChart.setOption(options)
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

    handleChange(value) {
      console.log(value)
    },
  },
}
</script>

<style scoped>
.app-container {
  margin-top: 40px;
  margin-left: 120px;
  margin-right: 120px;
}
</style>
