package cn.gyw.platform.gorm;

/**
 * 排序
 * 升序/降序
 */
public class Order {

    private boolean ascending;
    private String propertyName;

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
