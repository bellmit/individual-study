package cn.gyw.backend.system.dto;

import java.util.List;

public class RoleMenuRelationDto {

    private Long id;

    private List<Long> menuIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }
}