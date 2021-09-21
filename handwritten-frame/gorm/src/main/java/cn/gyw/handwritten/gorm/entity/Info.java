package cn.gyw.handwritten.gorm.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "t_info")
public class Info {

    private Integer id;
    private Integer memberId;
    @Column(name = "info_name")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "Info{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", name='" + name + '\'' +
                '}';
    }
}
