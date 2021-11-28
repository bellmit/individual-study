package cn.gyw.backend.resource.houseinfo.model.vo;

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
