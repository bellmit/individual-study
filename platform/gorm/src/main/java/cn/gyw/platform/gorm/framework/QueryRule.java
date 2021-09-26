package cn.gyw.platform.gorm.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询对象封装
 */
public class QueryRule {

    private static final int ASC_ORDER = 101;
    private static final int DESC_ORDER = 102;
    private static final int LIKE = 1;
    private static final int IN = 2;
    private static final int NOT_IN = 3;
    private static final int BETWEEN = 4;
    private static final int EQ = 5;
    private static final int NOT_EQ = 6;
    private static final int GT = 7;
    private static final int GE = 8;
    private static final int LT = 9;
    private static final int LE = 10;
    private static final int IS_NULL = 11;
    private static final int IS_NOT_NULL = 12;
    private static final int IS_EMPTY = 13;
    private static final int IS_NOT_EMPTY = 14;
    private static final int AND = 201;
    private static final int OR = 202;

    private List<Rule> ruleList = new ArrayList<>();
    private List<QueryRule> queryRuleList = new ArrayList<>();
    private String propertyName;

    public static QueryRule getInstance() {
        return new QueryRule();
    }

    private QueryRule() {}

    protected static class Rule{
        // 规则类型
        private int type;
        // 属性名
        private String propertyName;
        private Object[] values;
        private int andOr = AND;


    }
}
