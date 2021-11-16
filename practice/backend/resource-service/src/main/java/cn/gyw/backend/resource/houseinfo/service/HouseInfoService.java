package cn.gyw.backend.resource.houseinfo.service;

import cn.gyw.backend.resource.houseinfo.dao.po.HouseInfo;
import cn.gyw.components.web.base.mgb.IBaseService;

import java.time.LocalDate;

/**
 * 房屋信息类
 */
public interface HouseInfoService extends IBaseService<HouseInfo> {

    /**
     * 查询最大日期
     * @return 日期
     */
    LocalDate findMaxCrawlDate();
}
