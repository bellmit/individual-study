package cn.gyw.backend.resource.houseinfo.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VillageRankVo {

    private String province;
    private String city;
    private String district;
    private List<Village> villageList;

    @Getter
    @Setter
    public static class Village {
        private String name;
        private Double price;
    }
}
