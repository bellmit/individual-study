package cn.gyw.handwritten.gorm.framework;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 数据操作的顶层模板类
 * DAO
 * 模板模式
 */
public abstract class BaseDaoSupport<T> {

    // 实体操作
    private EntityOperation<T> op;

    // 读写分离
    private DataSource dataSourceReadOnly;
    private DataSource dataSourceWrite;

    void setDataSource(DataSource dataSource) {

    }

    void setDataSourceReadOnly(DataSource dataSource) {
        this.dataSourceReadOnly = dataSource;
    }

    void setDataSourceWrite(DataSource dataSource) {
        this.dataSourceWrite = dataSource;
    }

    protected JdbcTemplate jdbcTemplateReadOnly() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSourceReadOnly);
        return jdbcTemplate;
    }

    protected JdbcTemplate jdbcTemplateWrite() {
        return new JdbcTemplate(this.dataSourceWrite);
    }

    /**
     * 查询
     * @param queryRule
     * @return
     */
    protected List<T> select(QueryRule queryRule) {
        QueryRuleSqlBuilder sqlBuilder = new QueryRuleSqlBuilder(queryRule);
        String ws = remoteFirstAnd(sqlBuilder.getWhereSql());
        String whereSql = "".equals(ws) ? "" : (" where " + ws);
        String sql = "select " + "*" + " from " + getTableName() + whereSql;
        Object[] values = sqlBuilder.getValues();
        String orderSql = sqlBuilder.getOrderSql();
        orderSql = StringUtils.isEmpty(orderSql) ? " " : (" order by " + orderSql);
        sql += orderSql;
        System.out.println(sql);
        return this.jdbcTemplateReadOnly().query(sql, this.op.rowMapper, values);
    }

    /**
     * 包装器 原生JdbcTemplate 支持
     * @param sql
     * @param param
     * @return
     */
    protected List<Map<String, Object>> selectBySql(String sql, Map<String, ?> param) {
        return this.jdbcTemplateReadOnly().queryForList(sql, param);
    }

    protected String getTableName() {
        return this.op.tableName;
    }

    protected String remoteFirstAnd(String whereSql) {
        return null;
    }

    // 获取主建
    protected abstract String getPKColumn();
}
