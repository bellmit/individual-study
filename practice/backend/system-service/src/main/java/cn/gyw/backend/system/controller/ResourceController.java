package cn.gyw.backend.system.controller;

import cn.gyw.backend.system.entity.Resource;
import cn.gyw.backend.system.service.ResourceService;
import cn.gyw.components.web.base.mgb.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import cn.gyw.community.system.dto.ResourceDto;

@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController<Resource,ResourceDto> {

    @Autowired
    private ResourceService resourceService;
	
}
