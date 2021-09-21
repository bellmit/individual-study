package cn.gyw.frameworks.easypoi.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Record {

    @Excel(name = "姓名")
    private String name;

    @Excel(name = "项目", width = 50)
    private String item;

    @Excel(name = "总计")
    private String total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", item='" + item + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
