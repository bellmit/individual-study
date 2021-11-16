package cn.gyw.backend.resource.houseinfo.controller;

import cn.gyw.backend.resource.houseinfo.dao.po.HouseInfo;
import cn.gyw.backend.resource.houseinfo.dto.HouseInfoDto;
import cn.gyw.backend.resource.houseinfo.service.HouseInfoService;
import cn.gyw.components.web.base.mgb.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @description TODO
 * @createdTime 2021/10/20 15:13
 */
@Api(value = "房屋信息Controller", tags = {"房屋信息接口"})
@RestController
@RequestMapping("/house_info")
public class HouseInfoController
        extends BaseController<HouseInfoRequest, HouseInfo, HouseInfoDto> {

    @Resource
    private HouseInfoService houseInfoService;

    @GetMapping("/maxCrawlDate")
    public LocalDate query() {
        return houseInfoService.findMaxCrawlDate();
    }
}
