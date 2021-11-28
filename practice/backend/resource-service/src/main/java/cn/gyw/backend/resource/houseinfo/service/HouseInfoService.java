package cn.gyw.backend.resource.houseinfo.service;

import cn.gyw.backend.resource.houseinfo.dao.po.HouseInfo;
import cn.gyw.backend.resource.houseinfo.model.dto.HouseInfoDto;
import cn.gyw.backend.resource.houseinfo.model.vo.TreeData;
import cn.gyw.backend.resource.houseinfo.model.vo.VillageRankVo;
import cn.gyw.backend.resource.houseinfo.model.vo.VillageTrendVo;
import cn.gyw.components.web.base.mgb.IBaseService;

import java.time.LocalDate;
import java.util.List;

/**
 * 房屋信息类
 */
public interface HouseInfoService extends IBaseService<HouseInfo> {

    /**
     * 批量插入
     * @param data 数据列表
     */
    boolean batchInsert(List<HouseInfoDto> data);

    /**
     * 查询最大日期
     * @return 日期
     */
    LocalDate findMaxCrawlDate();

    /**
     * 小区价格排名
     */
    VillageRankVo queryVillageRank(String province, String city, String district);

    /**
     * 小区趋势
     * @param houseInfoDto 房屋Dto
     * @return 趋势Vo
     */
    VillageTrendVo queryVillageTrend(HouseInfoDto houseInfoDto);

    /**
     * 获取查询条件列表
     */
    List<TreeData> getQueryCondition();
}
