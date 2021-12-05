import BaseApi from '../base/BaseApi'
import request from '@/utils/request'
import apiTypes from '../base/api-types'
import LoggerFactory from "../base/logger";

class HouseInfoApi extends BaseApi {

  constructor() {
    super(apiTypes.HOUSE_INFO);
  }

  // 获取省市区查询条件
  getCondition() {
    return request({
      url: this.src + '/queryCondition',
      method: 'get'
    })
  }

  // 获取小区排名
  getVillageRank(params) {
    return request({
      url: this.src + '/villageRank',
      method: 'get',
      params: params
    })
  }

  getMinMaxOfCity(params) {
    return request({
      url: this.src + '/minMaxOfCity',
      method: 'get',
      params: params
    })
  }
}

let logger = LoggerFactory.getLogger(apiTypes.HOUSE_INFO)
let houseInfoApi = new HouseInfoApi();

export {
  houseInfoApi
}
