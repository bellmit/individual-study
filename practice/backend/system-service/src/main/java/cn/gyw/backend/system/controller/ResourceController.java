package cn.gyw.backend.system.controller;

import cn.gyw.backend.system.model.bo.ResourceRequest;
import cn.gyw.backend.system.model.dto.ResourceDto;
import cn.gyw.backend.system.model.entity.Resource;
import cn.gyw.backend.system.service.ResourceService;
import cn.gyw.components.web.base.mgb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController<ResourceRequest, Resource, ResourceDto> {

    @Autowired
    private ResourceService resourceService;

}
