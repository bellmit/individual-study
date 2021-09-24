import BaseApi from './base/BaseApi'
import request from '@/utils/request'
import apiTypes from './base/api-types'
import LoggerFactory from "./base/logger";

class SysRoleApi extends BaseApi {

  constructor() {
    super(apiTypes.SYS_ROLE);
  }

}

let logger = LoggerFactory.getLogger(apiTypes.SYS_ROLE)
let sysRoleApi = new SysRoleApi();

export {
  sysRoleApi
}
