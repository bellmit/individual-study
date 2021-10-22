package cn.gyw.backend.resource.houseinfo.controller;

import cn.gyw.backend.resource.houseinfo.dao.po.HouseInfo;
import cn.gyw.backend.resource.houseinfo.dto.HouseInfoDto;
import cn.gyw.backend.resource.houseinfo.service.HouseInfoServiceImpl;
import cn.gyw.components.web.base.mgb.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description TODO
 * @createdTime 2021/10/20 15:13
 */
@Api(value = "房屋信息Controller", tags = {"房屋信息接口"})
@RestController("/house_info")
public class HouseInfoController
        extends BaseController<HouseInfoRequest, HouseInfoResponse, HouseInfo, HouseInfoDto> {

    @Autowired
    private HouseInfoServiceImpl houseInfoService;
}
