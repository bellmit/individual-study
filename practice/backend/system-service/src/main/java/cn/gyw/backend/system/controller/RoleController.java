package cn.gyw.community.system.controller;

import cn.gyw.community.system.dto.RoleMenuRelationDto;
import cn.gyw.community.system.dto.RoleResourceRelationDto;
import cn.gyw.community.system.entity.Menu;
import cn.gyw.community.system.entity.Resource;
import cn.gyw.community.web.model.BaseResponse;
import cn.gyw.community.web.model.DataResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import cn.gyw.community.system.entity.Role;
import cn.gyw.community.system.dto.RoleDto;
import cn.gyw.community.system.service.RoleService;
import cn.gyw.community.web.base.mgb.BaseController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role,RoleDto> {

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色相关菜单
     */
    @GetMapping(value = "/listMenu/{roleId}")
    public DataResponse<List<Menu>> listMenu(@PathVariable Long roleId) {
        List<Menu> roleList = roleService.listMenu(roleId);
        return DataResponse.success(roleList);
    }

    /**
     * 获取角色相关资源
     */
    @GetMapping(value = "/listResource/{roleId}")
    public DataResponse<List<Resource>> listResource(@PathVariable Long roleId) {
        List<Resource> roleList = roleService.listResource(roleId);
        return DataResponse.success(roleList);
    }

    /**
     * 给角色分配菜单
     */
    @PostMapping(value = "/allocMenu")
    public BaseResponse allocMenu(@RequestBody RoleMenuRelationDto roleMenuRelationDto) {
        int count = roleService.allocMenu(roleMenuRelationDto.getId(), roleMenuRelationDto.getMenuIds());
        return DataResponse.success(count);
    }

    /**
     * 给角色分配资源
     */
    @PostMapping(value = "/allocResource")
    public BaseResponse allocResource(@RequestBody RoleResourceRelationDto roleResourceRelationDto) {
        int count = roleService.allocResource(roleResourceRelationDto.getId(), roleResourceRelationDto.getResourceIds());
        return DataResponse.success(count);
    }
}
