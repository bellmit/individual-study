package cn.gyw.corejava.base.model;

import cn.gyw.corejava.base.enums.FruitsType;

/**
 * @author yuewu.guan
 * @description TODO
 * @createdTime 2021/10/25 15:01
 */
public class Fruits {

    String desc;
    FruitsType type;
    Integer price;

    public Fruits(String desc, FruitsType type, Integer price) {
        this.desc = desc;
        this.type = type;
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public FruitsType getType() {
        return type;
    }

    public void setType(FruitsType type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Fruits{" +
                "desc='" + desc + '\'' +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}
