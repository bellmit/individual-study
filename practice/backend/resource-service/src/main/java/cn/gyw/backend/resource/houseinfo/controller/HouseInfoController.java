package cn.gyw.backend.resource.houseinfo.controller;

import cn.gyw.backend.resource.houseinfo.dao.po.HouseInfo;
import cn.gyw.backend.resource.houseinfo.model.HouseInfoRequest;
import cn.gyw.backend.resource.houseinfo.model.dto.HouseInfoDto;
import cn.gyw.backend.resource.houseinfo.model.vo.VillageRankVo;
import cn.gyw.backend.resource.houseinfo.model.vo.VillageTrendVo;
import cn.gyw.backend.resource.houseinfo.service.HouseInfoService;
import cn.gyw.components.web.base.mgb.BaseController;
import cn.gyw.components.web.enums.CommonRespEnum;
import cn.gyw.components.web.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;

@Api(value = "房屋信息Controller", tags = {"房屋信息接口"})
@RestController
@RequestMapping("/house_info")
public class HouseInfoController
        extends BaseController<HouseInfoRequest, HouseInfo, HouseInfoDto> {

    @Resource
    private HouseInfoService houseInfoService;

    @ApiOperation(value = "查询最大爬取时间")
    @GetMapping("/maxCrawlDate")
    public String queryMaxCrawlDate() {
        LocalDate localDate = houseInfoService.findMaxCrawlDate();
        return DateUtil.formatDate(localDate);
    }

    @ApiOperation(value = "查询上海各个区房屋小区价格排名")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "data.province", value = "省", paramType = "query", dataType = "String", required = true),
        @ApiImplicitParam(name = "data.city", value = "市", paramType = "query", dataType = "String", required = true),
        @ApiImplicitParam(name = "data.district", value = "区", paramType = "query", dataType = "String", required = true),
    })
    @GetMapping("/villageRank")
    public VillageRankVo villageRank(HouseInfoDto houseInfoDto) {
        CommonRespEnum.PARAM_NULL.assertNotNull(houseInfoDto, "请求参数异常");
        return houseInfoService.queryVillageRank(houseInfoDto.getProvince(), houseInfoDto.getCity(), houseInfoDto.getDistrict());
    }

    @GetMapping("villageTrend")
    public VillageTrendVo villageTrend(HouseInfoDto houseInfoDto) {
        CommonRespEnum.PARAM_NULL.assertNotNull(houseInfoDto, "请求参数异常");
        return null;
    }
}
