package cn.gyw.backend.service.system.dao;

import cn.gyw.backend.components.common.base.mgb.BaseDao;
import cn.gyw.backend.service.system.model.po.Menu;
import cn.gyw.backend.service.system.model.po.Resource;
import cn.gyw.backend.service.system.model.po.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色管理
 */
public interface RoleMapper extends BaseDao<Role> {

    /**
     * 根据后台用户ID获取菜单
     */
    List<Menu> getMenuList(@Param("adminId") Long adminId);

    /**
     * 根据角色ID获取菜单
     */
    List<Menu> getMenuListByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色ID获取资源
     */
    List<Resource> getResourceListByRoleId(@Param("roleId") Long roleId);
}