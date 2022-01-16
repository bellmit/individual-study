'use strict'
const path = require('path')
const defaultSettings = require('./src/settings.js')

function resolve(dir) {
  return path.join(__dirname, dir)
}

const name = defaultSettings.title || 'frontend' // page title
// If your port is set to 80,
// use administrator privileges to execute the command line.
// For example, Mac: sudo npm run
const port = 8087 // dev port

// All configuration item explanations can be find in https://cli.vuejs.org/config/
module.exports = {
  transpileDependencies: [
    'vue-echarts',
    'resize-detector'
  ],
  /**
   * You will need to set publicPath if you plan to deploy your site under a sub path,
   * for example GitHub Pages. If you plan to deploy your site to https://foo.github.io/bar/,
   * then publicPath should be set to "/bar/".
   * In most cases please use '/' !!!
   * Detail: https://cli.vuejs.org/config/#publicpath
   */
  publicPath: '/',
  outputDir: 'dist',
  assetsDir: 'static',
  // lintOnSave: process.env.NODE_ENV === 'development',
  // 取消ESlint
  lintOnSave: false,
  productionSourceMap: false,
  devServer: {
    port: port,
    open: false,
    overlay: {
      warnings: false,
      errors: true
    },
    proxy: {
      // mock VUE_APP_BASE_API -> mock js
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      [process.env.VUE_APP_BASE_API]: {
        target: `http://localhost:${port}/mock`,
        secure: false, // 如果是https接口，需要配置这个参数为true
        changeOrigin: true,
        pathRewrite: {
          ['^' + process.env.VUE_APP_BASE_API]: ''
        }
      },
      // Api gateway
      '/api': {
        target: 'http://localhost:8080',
        secure: false,
        changeOrigin: true,
        pathRewrite: {
          ['^/api']: ''
        }
      },
      // '/sys': {
      //   target: 'http://localhost:8081',
      //   // remote
      //   // target: 'http://192.168.1.181:8081',
      //   secure: false,
      //   changeOrigin: true,
      //   pathRewrite: {
      //     ['^/sys']: ''
      //   }
      // },
      // '/res': {
      //   target: 'http://localhost:8082',
      //   secure: false,
      //   changeOrigin: true,
      //   pathRewrite: {
      //     ['^/res']: ''
      //   }
      // },
      // '/chat-info': {
      //   target: 'http://localhost:8083/im',
      //   secure: false,
      //   changeOrigin: true
      // },
      // '/file': {
      //   target: 'http://localhost:8085',
      //   secure: false,
      //   changeOrigin: true
      // },
    },
    after: require('./mock/mock-server.js')
  },
  configureWebpack: {
    // provide the app's title in webpack's name field, so that
    // it can be accessed in index.html to inject the correct title.
    name: name,
    resolve: {
      alias: {
        '@': resolve('src'),
        'src': path.resolve(__dirname, '../src'),
        'components': path.resolve(__dirname, '../src/components'),
        'api': path.resolve(__dirname, '../src/api'),
        'utils': path.resolve(__dirname, '../src/utils'),
        'store': path.resolve(__dirname, '../src/store'),
        'router': path.resolve(__dirname, '../src/router')
      }
    }
  },
  chainWebpack(config) {
    config.plugins.delete('preload') // TODO: need test
    config.plugins.delete('prefetch') // TODO: need test

    // set preserveWhitespace
    config.module
      .rule('vue')
      .use('vue-loader')
      .loader('vue-loader')
      .tap(options => {
        options.compilerOptions.preserveWhitespace = true
        return options
      })
      .end()

    config
      // https://webpack.js.org/configuration/devtool/#development
      .when(process.env.NODE_ENV === 'development',
        config => config.devtool('cheap-source-map')
      )

    config
      .when(process.env.NODE_ENV !== 'development',
        config => {
          config
            .plugin('ScriptExtHtmlWebpackPlugin')
            .after('html')
            .use('script-ext-html-webpack-plugin', [{
              // `runtime` must same as runtimeChunk name. default is `runtime`
              inline: /runtime\..*\.js$/
            }])
            .end()
          config
            .optimization.splitChunks({
              chunks: 'all',
              cacheGroups: {
                libs: {
                  name: 'chunk-libs',
                  test: /[\\/]node_modules[\\/]/,
                  priority: 10,
                  chunks: 'initial' // only package third parties that are initially dependent
                },
                elementUI: {
                  name: 'chunk-elementUI', // split elementUI into a single package
                  priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
                  test: /[\\/]node_modules[\\/]_?element-ui(.*)/ // in order to adapt to cnpm
                },
                commons: {
                  name: 'chunk-commons',
                  test: resolve('src/components'), // can customize your rules
                  minChunks: 3, //  minimum common number
                  priority: 5,
                  reuseExistingChunk: true
                }
              }
            })
          config.optimization.runtimeChunk('single')
        }
      )
  }
}
