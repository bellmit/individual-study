package cn.gyw.backend.service.resource.service;

import cn.gyw.backend.components.common.base.mgb.IBaseService;
import cn.gyw.backend.service.resource.model.dto.HouseInfoDto;
import cn.gyw.backend.service.resource.model.po.HouseInfo;
import cn.gyw.backend.service.resource.model.vo.MinMaxOfCityVo;
import cn.gyw.backend.service.resource.model.vo.TreeData;
import cn.gyw.backend.service.resource.model.vo.VillageRankVo;
import cn.gyw.backend.service.resource.model.vo.VillageTrendVo;

import java.util.List;

/**
 * 房屋信息类
 */
public interface HouseInfoService extends IBaseService<HouseInfo> {

    /**
     * 批量插入
     *
     * @param data 数据列表
     */
    boolean batchInsert(List<HouseInfoDto> data);

    /**
     * 小区价格排名
     */
    VillageRankVo queryVillageRank(String crawlDate, String province, String city, String district);

    /**
     * 小区趋势
     *
     * @param houseInfoDto 房屋Dto
     * @return 趋势Vo
     */
    VillageTrendVo queryVillageTrend(HouseInfoDto houseInfoDto);

    /**
     * 获取查询条件列表
     */
    List<TreeData> getQueryCondition();

    /**
     * 查询城市的最高/最低价格小区
     */
    List<MinMaxOfCityVo> queryMinMaxOfCity(String crawlDate, String province, String city);
}
