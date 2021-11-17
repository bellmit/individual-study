const apiTypes = {
  // 系统管理模块
  SYS_USER: '/sys/admin',
  SYS_ROLE: '/sys/role',

  // 资源服务
  HOUSE_INFO: '/res/house_info'
}

// mock user api
apiTypes.SYS_USER = '/dev/admin'

export default apiTypes;