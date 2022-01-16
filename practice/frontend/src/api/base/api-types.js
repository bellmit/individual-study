// 接口路由前缀
const apiPrefix = "/api"

const apiTypes = {
  // 系统管理模块
  SYS_USER: apiPrefix + '/sys/admin',
  SYS_ROLE: apiPrefix + '/sys/role',

  // 资源服务
  HOUSE_INFO: apiPrefix + '/res/house_info'
}

// mock user api
apiTypes.SYS_USER = '/dev/admin'

export default apiTypes;