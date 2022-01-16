package cn.gyw.backend.service.resource.service;

import cn.gyw.backend.components.common.base.mgb.BaseService;
import cn.gyw.backend.components.common.utils.DateUtil;
import cn.gyw.backend.components.common.utils.RegexUtil;
import cn.gyw.backend.service.resource.dao.HouseInfoMapper;
import cn.gyw.backend.service.resource.enums.OriginTypeEnum;
import cn.gyw.backend.service.resource.model.dto.HouseInfoDto;
import cn.gyw.backend.service.resource.model.po.HouseInfo;
import cn.gyw.backend.service.resource.model.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HouseInfoServiceImpl extends BaseService<HouseInfo> implements HouseInfoService {

    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private HouseInfoMapper houseInfoMapper;

    @Autowired
    public void setHouseInfoMapper(HouseInfoMapper houseInfoMapper) {
        this.houseInfoMapper = houseInfoMapper;
    }

    @Override
    public boolean batchInsert(List<HouseInfoDto> data) {
        List<HouseInfo> houseInfoList = data.stream().map(houseInfoDto -> {
            HouseInfo houseInfo = new HouseInfo();
            BeanUtils.copyProperties(houseInfoDto, houseInfo);
            // 爬取日期
            if (StringUtils.isNotEmpty(houseInfoDto.getCrawlDate())) {
                houseInfo.setCrawlDate(LocalDate.parse(houseInfoDto.getCrawlDate(), dateFormatter));
            }
            // 价格处理
            String priceStr = houseInfoDto.getPrice().trim();
            BigDecimal price = RegexUtil.getBeginNum(priceStr);
            String priceUnit = priceStr.replaceFirst(RegexUtil.REGEX_NUM_OF_BEGIN, "");
            houseInfo.setPrice(price);
            houseInfo.setPriceUnit(priceUnit);
            // 数据来源
            houseInfo.setOriginType(OriginTypeEnum.getCode(houseInfoDto.getOriginType()));
            houseInfo.setHouseType(1);
            houseInfo.setUsage(1);
            return houseInfo;
        }).collect(Collectors.toList());
        try {
            houseInfoMapper.batchInsert(houseInfoList);
            return true;
        } catch (Exception e) {
            log.error("batchInsert error :", e);
        }
        return false;
    }

    @Override
    public VillageRankVo queryVillageRank(String crawlDate, String province, String city, String district) {
        LocalDate queryDate = LocalDate.parse(crawlDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        HouseInfo condition = new HouseInfo();
        condition.setProvince(province);
        condition.setCity(city);
        condition.setDistrict(district);
        condition.setCrawlDate(queryDate);
        Example example = new Example(HouseInfo.class);
        example.createCriteria().andEqualTo(condition);
        example.orderBy(HouseInfo.Fields.price).desc();

        List<HouseInfo> houseInfoList = houseInfoMapper.selectByExample(example);
        VillageRankVo rankVo = new VillageRankVo();
        BeanUtils.copyProperties(condition, rankVo);
        rankVo.setCrawlDate(DateUtil.formatDate(queryDate));
        rankVo.setVillageList(houseInfoList.stream().map(houseInfo -> {
            VillageVo village = new VillageVo();
            village.setCrawlDate(DateUtil.formatDate(houseInfo.getCrawlDate()));
            village.setName(houseInfo.getVillageName());
            village.setPrice(houseInfo.getPrice());
            return village;
        }).collect(Collectors.toList()));
        return rankVo;
    }

    @Override
    public VillageTrendVo queryVillageTrend(HouseInfoDto houseInfoDto) {
        HouseInfo condition = new HouseInfo();
        BeanUtils.copyProperties(houseInfoDto, condition);
        Example example = new Example(HouseInfo.class);
        example.createCriteria().andEqualTo(condition);
        example.orderBy(HouseInfo.Fields.crawlDate);

        List<HouseInfo> houseInfoList = houseInfoMapper.selectByExample(example);

        VillageTrendVo trendVo = new VillageTrendVo();
        BeanUtils.copyProperties(condition, trendVo);
        trendVo.setVillageList(houseInfoList.stream().map(this::buildVillageVo)
                .collect(Collectors.toList()));
        return trendVo;
    }

    @Override
    public List<TreeData> getQueryCondition() {
        List<HouseInfo> regionList = houseInfoMapper.queryRegionList();
        List<TreeData> treeDataList = new ArrayList<>();
        // TODO: 优化树状结构数据构造
        for (HouseInfo houseInfo : regionList) {
            if (StringUtils.isEmpty(houseInfo.getProvince())
                    || StringUtils.isEmpty(houseInfo.getCity())
                    || StringUtils.isEmpty(houseInfo.getDistrict())) {
                // 省市区空值数据，直接跳过
                continue;
            }
            // 省
            Optional<TreeData> provinceOp = treeDataList.stream()
                    .filter(item -> houseInfo.getProvince().equals(item.getValue()))
                    .findFirst();
            TreeData provinceNode;
            if (!provinceOp.isPresent()) {
                provinceNode = new TreeData(houseInfo.getProvince(), houseInfo.getProvince(), 1);
                treeDataList.add(provinceNode);
            } else {
                provinceNode = provinceOp.get();
            }
            // 市
            Optional<TreeData> cityOp = provinceNode.getChildren().stream()
                    .filter(item -> houseInfo.getCity().equals(item.getValue()))
                    .findFirst();
            TreeData cityNode;
            if (!cityOp.isPresent()) {
                cityNode = new TreeData(houseInfo.getCity(), houseInfo.getCity(), 2);
                provinceNode.getChildren().add(cityNode);
            } else {
                cityNode = cityOp.get();
            }
            // 区
            boolean flag = cityNode.getChildren().stream()
                    .anyMatch(item -> houseInfo.getDistrict().equals(item.getValue()));
            if (!flag) {
                cityNode.getChildren().add(new TreeData(houseInfo.getDistrict(), houseInfo.getDistrict(), 3));
            }
        }
        return treeDataList;
    }

    @Override
    public List<MinMaxOfCityVo> queryMinMaxOfCity(String crawlDate, String province, String city) {
        LocalDate queryDate = LocalDate.parse(crawlDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<MinMaxOfCityVo> result = houseInfoMapper.selectMinMaxPriceList(queryDate, province, city);
        return result;
    }

    private VillageVo buildVillageVo(HouseInfo houseInfo) {
        VillageVo village = new VillageVo();
        BeanUtils.copyProperties(houseInfo, village);
        village.setCrawlDate(DateUtil.formatDate(houseInfo.getCrawlDate()));
        return village;
    }
}
