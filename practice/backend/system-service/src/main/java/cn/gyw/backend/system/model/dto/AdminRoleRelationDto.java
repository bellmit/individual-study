package cn.gyw.backend.system.model.dto;

import java.util.List;

public class AdminRoleRelationDto {

    private Long id;

    private List<Long> roleIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "AdminRoleRelationDto{" +
                "id=" + id +
                ", roleIds=" + roleIds +
                '}';
    }
}