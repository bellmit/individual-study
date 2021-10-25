package cn.gyw.backend.system.controller;

import cn.gyw.backend.system.model.bo.RoleRequest;
import cn.gyw.backend.system.model.dto.RoleDto;
import cn.gyw.backend.system.model.dto.RoleMenuRelationDto;
import cn.gyw.backend.system.model.dto.RoleResourceRelationDto;
import cn.gyw.backend.system.model.entity.Menu;
import cn.gyw.backend.system.model.entity.Resource;
import cn.gyw.backend.system.model.entity.Role;
import cn.gyw.backend.system.service.RoleService;
import cn.gyw.components.web.base.mgb.BaseController;
import cn.gyw.components.web.model.BaseResponse;
import cn.gyw.components.web.model.DataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<RoleRequest, Role, RoleDto> {

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
