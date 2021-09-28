package cn.gyw.platform.gorm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询对象封装
 */
public final class QueryRule implements Serializable {

    protected static final int ASC_ORDER = 101;
    protected static final int DESC_ORDER = 102;
    protected static final int LIKE = 1;
    protected static final int IN = 2;
    protected static final int NOT_IN = 3;
    protected static final int BETWEEN = 4;
    protected static final int EQ = 5;
    protected static final int NOT_EQ = 6;
    protected static final int GT = 7;
    protected static final int GE = 8;
    protected static final int LT = 9;
    protected static final int LE = 10;
    protected static final int IS_NULL = 11;
    protected static final int IS_NOT_NULL = 12;
    protected static final int IS_EMPTY = 13;
    protected static final int IS_NOT_EMPTY = 14;
    protected static final int AND = 201;
    protected static final int OR = 202;

    private List<Rule> ruleList = new ArrayList<>();
    private List<QueryRule> queryRuleList = new ArrayList<>();
    private String propertyName;

    public static QueryRule getInstance() {
        return new QueryRule();
    }

    public void andEqual(String propertyName, Object value) {
        Rule rule = new Rule(EQ, propertyName);
        rule.setValues(new Object[]{value});
        ruleList.add(rule);
    }

    public void andGreaterEqual(String propertyName, Object value) {

    }

    public void andLessEqual(String propertyName, Object value) {

    }

    public void andNotEqual(String propertyName, Object value) {

    }

    public void andIsEmpty(String propertyName) {

    }

    public void andIsNotEmpty(String propertyName) {

    }

    public void andLike(String propertyName, Object value) {

    }

    public void orEqual(String propertyName, Object value) {
        Rule rule = new Rule(EQ, propertyName);
        rule.setValues(new Object[]{value});
        rule.setAndOr(OR);
        ruleList.add(rule);
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    private QueryRule() {
    }

    private QueryRule(String propertyName) {
        this.propertyName = propertyName;
    }

    protected static class Rule implements Serializable {

        // 规则类型
        private int type;
        // 属性名
        private String propertyName;
        private Object[] values;
        private int andOr = AND;

        public Rule(int paramInt, String paramString) {
            this.propertyName = paramString;
            this.type = paramInt;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public Object[] getValues() {
            return values;
        }

        public void setValues(Object[] values) {
            this.values = values;
        }

        public int getAndOr() {
            return andOr;
        }

        public void setAndOr(int andOr) {
            this.andOr = andOr;
        }
    }
}
