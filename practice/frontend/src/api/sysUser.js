import BaseApi from './base/BaseApi'
import request from '@/utils/request'
import apiTypes from './base/api-types'
import LoggerFactory from "./base/logger";

class SysUserApi extends BaseApi {

  constructor() {
    super(apiTypes.SYS_USER);
  }

  login({ username, password }) {
    return request({
      url: this.src + '/login',
      method: 'post',
      data: {
        username,
        password
      }
    })
  }

  getInfo() {
    return request({
      url: this.src + '/info',
      method: 'get',
    })
  }

  logout() {
    return request({
      url: this.src + '/logout',
      method: 'post'
    })
  }

}

let logger = LoggerFactory.getLogger(apiTypes.SYS_USER)
let sysUserApi = new SysUserApi();

export {
  sysUserApi
}
