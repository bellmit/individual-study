package cn.gyw.backend.service.resource.model.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Description 房屋信息
 * @createdTime 2021/9/30 15:17
 */
@Getter
@Setter
@FieldNameConstants
@Table(name = "house_info")
public class HouseInfo {

    private Long id;
    // 爬取日期
    private LocalDate crawlDate;
    // 省份
    private String province;
    // 城市
    private String city;
    // 小区的名字
    private String villageName;
    // 价格
    private BigDecimal price;
    // 价格单位
    private String priceUnit;
    // 几居。列表
    private String rooms;
    // 面积
    private String area;
    // 地址
    private String address;
    // 行政区
    private String district;
    // 是否在售
    private String sale;
    // 详情页面url
    private String detailUrl;
    // 房屋类型：1->新房；2->二手房；
    private Integer houseType;
    // 用途：0->购房；1->租房
    @Column(name = "`usage`")
    private Integer usage;
    // 数据来源：0->WEB
    private Integer originType;
    // 创建时间
    private LocalDateTime createdTime;
    // 修改时间
    private LocalDateTime modifiedTime;
}
