package cn.gyw.backend.service.resource.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VillageRankVo {

    private String crawlDate;
    private String province;
    private String city;
    private String district;

    private List<VillageVo> villageList;
}
