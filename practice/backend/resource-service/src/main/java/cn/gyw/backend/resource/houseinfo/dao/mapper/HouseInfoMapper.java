package cn.gyw.backend.resource.houseinfo.dao.mapper;

import cn.gyw.backend.resource.houseinfo.dao.po.HouseInfo;
import cn.gyw.components.web.base.mgb.BaseDao;

import java.util.List;

public interface HouseInfoMapper extends BaseDao<HouseInfo> {

    /**
     * 批量插入
     * @param data 数据列表
     */
    void batchInsert(List<HouseInfo> data);

}
