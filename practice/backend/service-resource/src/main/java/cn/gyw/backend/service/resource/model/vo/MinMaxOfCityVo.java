package cn.gyw.backend.service.resource.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MinMaxOfCityVo {

    private String crawlDate;
    private String province;
    private String city;
    private String district;

    private BigDecimal minPrice;
    private BigDecimal maxPrice;

}
