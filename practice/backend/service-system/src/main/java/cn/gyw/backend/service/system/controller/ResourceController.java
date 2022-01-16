package cn.gyw.backend.service.system.controller;

import cn.gyw.backend.components.common.base.mgb.BaseController;
import cn.gyw.backend.service.system.biz.ResourceService;
import cn.gyw.backend.service.system.model.dto.ResourceDto;
import cn.gyw.backend.service.system.model.po.Resource;
import cn.gyw.backend.service.system.model.vo.ResourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/resource")
public class ResourceController extends BaseController<ResourceRequest, Resource, ResourceDto> {

    @Autowired
    private ResourceService resourceService;

}
