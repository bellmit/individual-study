package cn.gyw.backend.system.dto;

import java.io.Serializable;

/**
 * 角色
 */
public class RoleDto implements Serializable {

    private static final long serialVersionUID = -3790569939591490128L;

    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色状态 0 启用 1 禁用
     */
    private Integer roleStatus;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    @Override
    public String toString() {
        return "RoleDto{" + "roleName='" + roleName + '\'' + ", roleStatus=" + roleStatus + '}';
    }
}
