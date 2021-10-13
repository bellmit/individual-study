import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import enLocale from 'element-ui/lib/locale/lang/en' // lang i18n
import cnLocale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

// v4 版本全部组件引入 也可以按需引入
import echarts from 'echarts'
import VCharts from 'v-charts'

import App from './App'
import axios from 'axios'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

import '@/directive/dialog-drag.js'
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
import { mockXHR } from '../mock'
if (process.env.NODE_ENV === 'production') {
  console.log("mockXHR")
  mockXHR()
}

// set ElementUI lang to EN
// Vue.use(ElementUI, { enLocale })
// set ElementUI lang to CN
Vue.use(ElementUI, { cnLocale })
Vue.use(VCharts)

Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.BASE_APIS = null
Vue.prototype.$echarts = echarts

function getServerConfig() {
  return new Promise((resolve, reject) => {
    axios.get('./serverConfig.json').then((result) => {
      // console.log(result) // 看打印出来的结果
      const config = result.data
      for (const key in config) {
        Vue.prototype[key] = config[key]
      }
      // 验证是否已经把属性挂在了Vue上
      // console.log(Vue.prototype.BASE_ADDR)
      resolve()
    }).catch((error) => {
      console.log(error)
      reject()
    });
  })
}

async function init() {
  await getServerConfig()
  // axios.defaults.baseURL = Vue.prototype.BASE_API // use gateway ,add prefix '/api's
  new Vue({
    router,
    store,
    render: h => h(App)
  }).$mount('#app')
}

init()
