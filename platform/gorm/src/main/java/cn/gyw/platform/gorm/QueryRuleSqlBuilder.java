package cn.gyw.platform.gorm;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询对象解析器
 * 将查询对象转换成SQL语句
 */
public class QueryRuleSqlBuilder {

    public static int CUR_INDEX;

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
        CUR_INDEX = 0;
        this.propertyList = new ArrayList<>();
        this.valueList = new ArrayList<>();
        this.orderList = new ArrayList<>();
        for (QueryRule.Rule rule : queryRule.getRuleList()) {
            switch (rule.getType()) {
                case QueryRule.BETWEEN:
                    processBetween(rule);
                    break;
                case QueryRule.EQ:
                    processEqual(rule);
                    break;
                default:
                    throw new RuntimeException("不支持的规则类型：" + rule.getType());
            }
        }

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

    /**
     * 处理between
     * @param rule
     */
    private void processBetween(QueryRule.Rule rule) {
        if (rule.getValues() == null || rule.getValues().length < 2) {
            return;
        }
        add(rule.getAndOr(), rule.getPropertyName(), "", "between", rule.getValues()[0], "and");
        add(0, "", "", "", rule.getValues()[1], "");
    }

    private void processEqual(QueryRule.Rule rule) {
        if (rule.getValues() == null || rule.getValues().length != 1) {
            return;
        }
        add(rule.getAndOr(), rule.getPropertyName(), "", "and", rule.getValues()[0], "");
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

    private void add(int andOr, String key, String split, String prefix, Object values, String suffix) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix).append(key).append(split).append(values).append(suffix);
        System.out.println(builder.toString());
        whereSql += builder.toString();
    }
}
