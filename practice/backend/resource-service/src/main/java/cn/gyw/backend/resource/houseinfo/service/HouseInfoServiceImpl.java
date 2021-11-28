package cn.gyw.backend.resource.houseinfo.service;

import cn.gyw.backend.resource.enums.OriginTypeEnum;
import cn.gyw.backend.resource.houseinfo.dao.mapper.HouseInfoMapper;
import cn.gyw.backend.resource.houseinfo.dao.po.HouseInfo;
import cn.gyw.backend.resource.houseinfo.model.dto.HouseInfoDto;
import cn.gyw.backend.resource.houseinfo.model.vo.TreeData;
import cn.gyw.backend.resource.houseinfo.model.vo.VillageRankVo;
import cn.gyw.backend.resource.houseinfo.model.vo.VillageTrendVo;
import cn.gyw.backend.resource.houseinfo.model.vo.VillageVo;
import cn.gyw.components.web.base.mgb.BaseService;
import cn.gyw.components.web.utils.DateUtil;
import cn.gyw.components.web.utils.RegexUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    public LocalDate findMaxCrawlDate() {
        return houseInfoMapper.queryMaxCrawlDate();
    }

    @Override
    public VillageRankVo queryVillageRank(String province, String city, String district) {
        LocalDate maxCrawlDate = houseInfoMapper.queryMaxCrawlDate();
        HouseInfo condition = new HouseInfo();
        condition.setProvince(province);
        condition.setCity(city);
        condition.setDistrict(district);
        condition.setCrawlDate(maxCrawlDate);
        Example example = new Example(HouseInfo.class);
        example.createCriteria().andEqualTo(condition);
        example.orderBy(HouseInfo.Fields.price).desc();

        List<HouseInfo> houseInfoList = houseInfoMapper.selectByExample(example);
        VillageRankVo rankVo = new VillageRankVo();
        BeanUtils.copyProperties(condition, rankVo);
        rankVo.setCrawlDate(DateUtil.formatDate(maxCrawlDate));
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
        trendVo.setVillageList(houseInfoList.stream().map(houseInfo -> {
            VillageVo village = new VillageVo();
            BeanUtils.copyProperties(houseInfo, village);
            village.setCrawlDate(DateUtil.formatDate(houseInfo.getCrawlDate()));
            return village;
        }).collect(Collectors.toList()));
        return trendVo;
    }

    @Override
    public List<TreeData> getQueryCondition() {
        List<HouseInfo> regionList = houseInfoMapper.queryRegionList();
        List<TreeData> treeDataList = new ArrayList<>();
        for (HouseInfo houseInfo : regionList) {
            // 省
            Optional<TreeData> provinceOp = treeDataList.stream()
                    .filter(item -> houseInfo.getProvince().equals(item.getName()))
                    .findFirst();
            TreeData provinceNode;
            if (!provinceOp.isPresent()) {
                provinceNode = new TreeData(1, houseInfo.getProvince());
                treeDataList.add(provinceNode);
            } else {
                provinceNode = provinceOp.get();
            }
            // 市
            Optional<TreeData> cityOp =provinceNode.getChildren().stream()
                    .filter(item -> houseInfo.getCity().equals(item.getName()))
                    .findFirst();
            TreeData cityNode;
            if (!cityOp.isPresent()) {
                cityNode = new TreeData(2, houseInfo.getCity());
                provinceNode.getChildren().add(cityNode);
            } else {
                cityNode = cityOp.get();
            }
            // 区
            boolean flag = cityNode.getChildren().stream()
                    .anyMatch(item -> houseInfo.getDistrict().equals(item.getName()));
            if (!flag) {
                cityNode.getChildren().add(new TreeData(3, houseInfo.getDistrict()));
            }
        }
        return treeDataList;
    }
}
