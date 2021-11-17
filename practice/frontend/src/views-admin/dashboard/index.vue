<template>
  <div class="app-container">
    <dl>
      <dt>1.同一天同一个区不同小区价格从高到底排名</dt>
      <dt>2.不同小区最高价格，最低价格柱状图</dt>
    </dl>
    <el-row>
      <p>柱状图</p>
      <div id="myChart" :style="{ width: '500px', height: '500px' }" />
    </el-row>
    <el-row>
      <p>饼状图</p>
      <div id="pieChart" :style="{ width: '600px', height: '500px' }" />
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {}
  },
  created() {},
  mounted() {
    // 模版渲染后调用绘制图表的方法
    this.drawLine()
    this.drawPie()
  },
  methods: {
    drawLine() {
      // 初始化echarts实例
      const myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表
      myChart.setOption({
  legend: {},
  tooltip: {},
  dataset: {
    dimensions: ['product', '2015', '2016', '2017'],
    source: [
      { product: 'Matcha Latte', 2015: 43.3, 2016: 85.8, 2017: 93.7 },
      { product: 'Milk Tea', 2015: 83.1, 2016: 73.4, 2017: 55.1 },
      { product: 'Cheese Cocoa', 2015: 86.4, 2016: 65.2, 2017: 82.5 },
      { product: 'Walnut Brownie', 2015: 72.4, 2016: 53.9, 2017: 39.1 }
    ]
  },
  xAxis: { type: 'category' },
  yAxis: {},
  // Declare several bar series, each will be mapped
  // to a column of dataset.source by default.
  series: [{ type: 'bar' }, { type: 'bar' }, { type: 'bar' }]
})
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
