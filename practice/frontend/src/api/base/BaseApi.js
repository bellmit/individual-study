import request from '@/utils/request'

import LoggerFactory from "./logger";

class BaseApi {

    constructor(target) {
        this.src = target;
    }

    find(params) {
        logger.info("find for " + this.src)
        return request({
            url: this.src,
            method: 'GET',
            params: params
        })
    }

    findByPage(params) {
        logger.info("findByPage for " + this.src)
        return request({
            url: this.src + '/',
            method: 'GET',
            params: params
        })
    }

    remove(params) {
        logger.info("remove for " + this.src)
        return request({
            url: this.src + "/" + params,
            method: 'DELETE',
            params: params
        })
    }

    offer(data) {
        logger.info("offer for " + this.src)
        return request({
            url: this.src,
            method: 'POST',
            data: data
        })
    }

    patch(data) {
        logger.info("patch for " + this.src)
        return request({
            url: this.src,
            method: 'PUT',
            data: data
        });
    }
}

let logger = LoggerFactory.getLogger("BaseApi");

export default BaseApi;