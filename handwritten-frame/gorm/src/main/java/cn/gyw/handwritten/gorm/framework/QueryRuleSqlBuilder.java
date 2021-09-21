package cn.gyw.handwritten.gorm.framework;

import java.util.List;

/**
 * 查询对象解析器
 * 将查询对象转换成SQL语句
 */
public class QueryRuleSqlBuilder {

    // 保存列名列表
    private List<String> propertyList;
    // 参数值列表
    private List<Object> valueList;
    // 排序规则列表
    private List<Object> orderList;

    private String whereSql;
    private String orderSql;

    public QueryRuleSqlBuilder(QueryRule queryRule) {
        // 解析操作类型

        // 拼接where
        appendWhereSql();
        // 拼接order
        appendOrderSql();
        // 拼接参数值
        appendValues();
    }

    private void appendValues() {
    }

    private void appendOrderSql() {
    }

    private void appendWhereSql() {
    }

    private void processBetween(QueryRule.Rule rule) {


    }

    private void add(int andOr, String key, String split, String prefix, Object[] values, String suffix) {

    }


    public String getWhereSql() {
        return whereSql;
    }

    public String getOrderSql() {
        return orderSql;
    }

    public List<String> getPropertyList() {
        return propertyList;
    }

    public List<Object> getValueList() {
        return valueList;
    }

    public Object[] getValues() {
        return valueList.toArray();
    }

    public List<Object> getOrderList() {
        return orderList;
    }
}
