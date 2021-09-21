package cn.gyw.backend.system.controller;

import cn.gyw.backend.system.dto.AdminDto;
import cn.gyw.backend.system.entity.Admin;
import cn.gyw.backend.system.service.AdminService;
import cn.gyw.components.web.base.mgb.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "AdminController", description = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController<Admin,AdminDto> {

    @Autowired
    private AdminService adminService;

}
