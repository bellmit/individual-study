package cn.gyw.backend.system.dao;

import cn.gyw.backend.system.model.entity.Menu;
import cn.gyw.backend.system.model.entity.Resource;
import cn.gyw.backend.system.model.entity.Role;
import cn.gyw.components.web.base.mgb.BaseDao;
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