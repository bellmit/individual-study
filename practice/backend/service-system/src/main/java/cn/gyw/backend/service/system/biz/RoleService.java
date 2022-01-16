package cn.gyw.backend.service.system.biz;

import cn.gyw.backend.components.common.base.mgb.BaseService;
import cn.gyw.backend.service.system.biz.interfaces.IAdminCacheService;
import cn.gyw.backend.service.system.dao.RoleMapper;
import cn.gyw.backend.service.system.dao.RoleMenuRelationMapper;
import cn.gyw.backend.service.system.dao.RoleResourceRelationMapper;
import cn.gyw.backend.service.system.model.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class RoleService extends BaseService<Role> {

	@Autowired
    private RoleMapper roleMapper;
	@Autowired
	private RoleMenuRelationMapper roleMenuRelationMapper;
	@Autowired
	private RoleResourceRelationMapper roleResourceRelationMapper;

	@Autowired
	private IAdminCacheService adminCacheService;

    public List<Menu> getMenuList(Long adminId) {
        return roleMapper.getMenuList(adminId);
    }

    public List<Menu> listMenu(Long roleId) {
        return roleMapper.getMenuListByRoleId(roleId);
    }

    public List<Resource> listResource(Long roleId) {
        return roleMapper.getResourceListByRoleId(roleId);
    }

    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        RoleMenuRelation roleMenuRelation = new RoleMenuRelation();
        roleMenuRelation.setRoleId(roleId);
        Example example = new Example(RoleMenuRelation.class);
        example.createCriteria().andEqualTo(roleMenuRelation);
        roleMenuRelationMapper.deleteByExample(example);
        //批量插入新关系
        for (Long menuId : menuIds) {
            RoleMenuRelation relation = new RoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            roleMenuRelationMapper.insert(relation);
        }
        return menuIds.size();
    }

    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
        roleResourceRelation.setRoleId(roleId);
        Example example = new Example(RoleMenuRelation.class);
        example.createCriteria().andEqualTo(roleResourceRelation);
        roleResourceRelationMapper.deleteByExample(example);
        //批量插入新关系
        for (Long resourceId : resourceIds) {
            RoleResourceRelation relation = new RoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            roleResourceRelationMapper.insert(relation);
        }
        adminCacheService.delResourceListByRole(roleId);
        return resourceIds.size();
    }
}
