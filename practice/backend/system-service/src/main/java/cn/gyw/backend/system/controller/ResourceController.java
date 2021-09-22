package cn.gyw.community.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import cn.gyw.community.system.entity.Resource;
import cn.gyw.community.system.dto.ResourceDto;
import cn.gyw.community.system.service.ResourceService;
import cn.gyw.community.web.base.mgb.BaseController;

@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController<Resource,ResourceDto> {

    @Autowired
    private ResourceService resourceService;
	
}
