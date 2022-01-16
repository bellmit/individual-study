package cn.gyw.backend.service.system.dao;

import cn.gyw.backend.components.common.base.mgb.BaseDao;
import cn.gyw.backend.service.system.model.po.AdminRoleRelation;
import cn.gyw.backend.service.system.model.po.Resource;
import cn.gyw.backend.service.system.model.po.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户与角色关系管理
 */
public interface AdminRoleRelationMapper extends BaseDao<AdminRoleRelation> {

    /**
     * 批量插入用户角色关系
     */
    int insertList(@Param("list") List<AdminRoleRelation> adminRoleRelationList);

    /**
     * 获取用于所有角色
     */
    List<Role> getRoleList(@Param("adminId") Long adminId);

    /**
     * 获取用户所有可访问资源
     */
    List<Resource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 获取资源相关用户ID列表
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);

}