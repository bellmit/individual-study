package cn.gyw.backend.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import cn.gyw.backend.system.entity.Role;
import cn.gyw.backend.system.dto.RoleDto;
import cn.gyw.backend.system.service.RoleService;
import cn.gyw.components.web.base.mgb.BaseController;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role,RoleDto> {

    @Autowired
    private RoleService roleService;
	
}
