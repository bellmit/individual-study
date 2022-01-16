package cn.gyw.backend.service.resource.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VillageVo {

    private String crawlDate;
    private String name;
    private BigDecimal price;

}
