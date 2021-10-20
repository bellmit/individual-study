package cn.gyw.backend.resource.houseinfo.dao.po;

import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @Description 服务信息
 * @createdTime 2021/9/30 15:17
 */
@Table(name = "house_info")
public class HouseInfo {

    private Long id;
    // 省份
    private String province;
    // 城市
    private String city;
    // 小区的名字
    private String villageName;
    // 价格
    private String price;
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
    private Short houseType;
    // 用途：0->购房；1->租房
    private Short usage;
    // 数据来源：0->WEB
    private Short originType;
    // 创建时间
    private LocalDateTime createdTime;
    // 修改时间
    private LocalDateTime modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public Short getHouseType() {
        return houseType;
    }

    public void setHouseType(Short houseType) {
        this.houseType = houseType;
    }

    public Short getUsage() {
        return usage;
    }

    public void setUsage(Short usage) {
        this.usage = usage;
    }

    public Short getOriginType() {
        return originType;
    }

    public void setOriginType(Short originType) {
        this.originType = originType;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
