package cn.gyw.backend.system.model.dto;

import java.util.List;

public class RoleResourceRelationDto {

    private Long id;

    private List<Long> resourceIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }
}