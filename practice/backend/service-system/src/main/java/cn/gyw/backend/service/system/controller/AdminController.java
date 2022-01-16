package cn.gyw.backend.service.system.controller;

import cn.gyw.backend.components.common.base.mgb.BaseController;
import cn.gyw.backend.components.common.enums.CommonRespEnum;
import cn.gyw.backend.components.common.model.BaseResponse;
import cn.gyw.backend.components.common.model.DataResponse;
import cn.gyw.backend.service.system.biz.AdminService;
import cn.gyw.backend.service.system.biz.RoleService;
import cn.gyw.backend.service.system.enums.SystemRespEnum;
import cn.gyw.backend.service.system.model.dto.AdminDto;
import cn.gyw.backend.service.system.model.dto.AdminRoleRelationDto;
import cn.gyw.backend.service.system.model.dto.UserLoginParam;
import cn.gyw.backend.service.system.model.po.Admin;
import cn.gyw.backend.service.system.model.po.Role;
import cn.gyw.backend.service.system.model.vo.AdminRequest;
import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 */
@RestController
@RequestMapping("/sys/admin")
public class AdminController extends BaseController<AdminRequest, Admin, AdminDto> {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    /**
     * 用户注册
     *
     * @param adminDto
     * @return
     */
    @PostMapping(value = "/register")
    public BaseResponse register(@Validated @RequestBody AdminDto adminDto) {
        Admin umsAdmin = adminService.register(adminDto);
        if (umsAdmin == null) {
            return BaseResponse.error(SystemRespEnum.REGISTER_FAILED);
        }
        return DataResponse.success(umsAdmin);
    }

    @PostMapping(value = "/login")
    public BaseResponse login(@Validated @RequestBody UserLoginParam userLoginParam) {
        String token = adminService.login(userLoginParam.getUsername(), userLoginParam.getPassword());
        SystemRespEnum.LOGIN_FAILED.assertNotNull(token);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return DataResponse.success(tokenMap);
    }

    @GetMapping("/info")
    public BaseResponse getInfo(Principal principal) {
        CommonRespEnum.UN_AUTHORIZED.assertNotNull(principal);
        String username = principal.getName();
        Admin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        List<Role> roleList = adminService.getRoleList(umsAdmin.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(Role::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }
        return DataResponse.success(data);
    }

    @PostMapping("/logout")
    public BaseResponse logout() {
        return BaseResponse.success();
    }

    /**
     * 分配角色
     */
    @PostMapping(value = "/allocRole")
    public BaseResponse allocRole(@RequestBody AdminRoleRelationDto adminRoleRelationDto) {
        log.info("/allocRole request :{}", adminRoleRelationDto);
        int count = adminService.updateRole(adminRoleRelationDto.getId(), adminRoleRelationDto.getRoleIds());
        if (count >= 0) {
            return DataResponse.success(count);
        }
        return BaseResponse.error(SystemRespEnum.ALLOC_ROLE_FAILED);
    }
}
