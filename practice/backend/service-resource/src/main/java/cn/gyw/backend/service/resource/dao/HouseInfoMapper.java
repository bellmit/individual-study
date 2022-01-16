package cn.gyw.backend.service.resource.dao;

import cn.gyw.backend.components.common.base.mgb.BaseDao;
import cn.gyw.backend.service.resource.model.po.HouseInfo;
import cn.gyw.backend.service.resource.model.vo.MinMaxOfCityVo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface HouseInfoMapper extends BaseDao<HouseInfo> {

    /**
     * 批量插入
     *
     * @param data 数据列表
     */
    void batchInsert(List<HouseInfo> data);

    /**
     * 查询指定日期区间内，所有的爬取日期
     *
     * @return 日期列表
     */
    List<LocalDate> selectAllCrawlDate(LocalDate startDate, LocalDate endDate);

    /**
     * 小区排行榜
     *
     * @param province  省
     * @param city      市
     * @param district  区
     * @param crawlDate 爬取日期
     * @return 小区列表
     */
    List<HouseInfo> queryVillageRank(String province, String city, String district, LocalDate crawlDate);

    /**
     * 查询区域列表
     */
    List<HouseInfo> queryRegionList();

    /**
     * 指定市，不同区的最低和最高价格列表
     */
    List<MinMaxOfCityVo> selectMinMaxPriceList(LocalDate crawlDate, String province, String city);
}
