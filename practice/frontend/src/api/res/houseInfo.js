import BaseApi from '../base/BaseApi'
import request from '@/utils/request'
import apiTypes from '../base/api-types'
import LoggerFactory from "../base/logger";

class HouseInfoApi extends BaseApi {

  constructor() {
    super(apiTypes.HOUSE_INFO);
  }

}

let logger = LoggerFactory.getLogger(apiTypes.HOUSE_INFO)
let houseInfoApi = new HouseInfoApi();

export {
  houseInfoApi
}
