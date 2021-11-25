package cn.gyw.backend.resource.houseinfo.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 小区趋势
 *
 * @author yuewu.guan
 * @date 2021/11/25 19:15
 */
@Getter
@Setter
public class VillageTrendVo {

    private String province;
    private String city;
    private String district;
    private List<VillageVo> villageList;

}
