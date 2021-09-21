package cn.gyw.backend.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import cn.gyw.backend.system.entity.Resource;
import cn.gyw.backend.system.dto.ResourceDto;
import cn.gyw.backend.system.service.ResourceService;
import cn.gyw.components.web.base.mgb.BaseController;

@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController<Resource,ResourceDto> {

    @Autowired
    private ResourceService resourceService;
	
}
