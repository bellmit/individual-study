package cn.gyw.backend.service.system.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminRoleRelationDto {

    private Long id;

    private List<Long> roleIds;

}