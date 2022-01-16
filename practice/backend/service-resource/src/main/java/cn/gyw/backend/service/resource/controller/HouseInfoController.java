package cn.gyw.backend.service.resource.controller;

import cn.gyw.backend.components.common.base.mgb.BaseController;
import cn.gyw.backend.components.common.enums.CommonRespEnum;
import cn.gyw.backend.service.resource.model.HouseInfoRequest;
import cn.gyw.backend.service.resource.model.dto.HouseInfoDto;
import cn.gyw.backend.service.resource.model.po.HouseInfo;
import cn.gyw.backend.service.resource.model.vo.MinMaxOfCityVo;
import cn.gyw.backend.service.resource.model.vo.TreeData;
import cn.gyw.backend.service.resource.model.vo.VillageRankVo;
import cn.gyw.backend.service.resource.model.vo.VillageTrendVo;
import cn.gyw.backend.service.resource.service.HouseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "房屋信息Controller", tags = {"房屋信息接口"})
@RestController
@RequestMapping("/res/house_info")
public class HouseInfoController
        extends BaseController<HouseInfoRequest, HouseInfo, HouseInfoDto> {

    @Resource
    private HouseInfoService houseInfoService;

    @ApiOperation(value = "获取省市区查询条件列表")
    @GetMapping("/queryCondition")
    public List<TreeData> queryCondition() {
        return houseInfoService.getQueryCondition();
    }

    @ApiOperation(value = "查询指定小区价格排名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "crawlDate", value = "爬取日期", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "province", value = "省", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "city", value = "市", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "district", value = "区", paramType = "query", dataType = "String", required = true),
    })
    @GetMapping("/villageRank")
    public VillageRankVo villageRank(@ApiIgnore HouseInfoDto houseInfoDto) {
        CommonRespEnum.PARAM_NULL.assertAllNotNull(houseInfoDto, HouseInfoDto.Fields.crawlDate,
                HouseInfoDto.Fields.province, HouseInfoDto.Fields.city, HouseInfoDto.Fields.district);
        return houseInfoService.queryVillageRank(houseInfoDto.getCrawlDate(), houseInfoDto.getProvince(), houseInfoDto.getCity(), houseInfoDto.getDistrict());
    }

    @ApiOperation(value = "指定市，不同区的最低和最高价格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province", value = "省", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "city", value = "市", paramType = "query", dataType = "String", required = true)
    })
    @GetMapping("/minMaxOfCity")
    public List<MinMaxOfCityVo> minMaxOfCity(@ApiIgnore HouseInfoDto houseInfoDto) {
        CommonRespEnum.PARAM_NULL.assertAllNotNull(houseInfoDto,
                HouseInfoDto.Fields.crawlDate, HouseInfoDto.Fields.province, HouseInfoDto.Fields.city);
        return houseInfoService.queryMinMaxOfCity(houseInfoDto.getCrawlDate(), houseInfoDto.getProvince(), houseInfoDto.getCity());
    }

    @ApiOperation(value = "查询指定小区价格趋势")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province", value = "省", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "city", value = "市", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(name = "district", value = "区", paramType = "query", dataType = "String", required = true),
    })
    @GetMapping("villageTrend")
    public VillageTrendVo villageTrend(@ApiIgnore HouseInfoDto houseInfoDto) {
        CommonRespEnum.PARAM_NULL.assertAllNotNull(houseInfoDto, HouseInfoDto.Fields.province, HouseInfoDto.Fields.city,
                HouseInfoDto.Fields.district);
        return null;
    }
}
