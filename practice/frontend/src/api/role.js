import BaseApi from './base/BaseApi'
import request from '@/utils/request'
import apiTypes from './base/api-types'
import LoggerFactory from "./base/logger";

class SysRoleApi extends BaseApi {

  constructor() {
    super(apiTypes.SYS_USER);
  }

}

let logger = LoggerFactory.getLogger(apiTypes.SYS_USER)
let sysRoleApi = new SysRoleApi();

export {
  sysRoleApi
}
