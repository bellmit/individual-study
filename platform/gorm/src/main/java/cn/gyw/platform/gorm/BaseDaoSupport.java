package cn.gyw.platform.gorm;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * 数据操作的顶层模板类
 * DAO
 * 模板模式
 */
public abstract class BaseDaoSupport<T,ID> {

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

    public BaseDaoSupport() {
        try {
            this.init();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void init() throws Exception {
        Type genericInterfaces = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genericInterfaces).getActualTypeArguments();
        Class<T> entityClass = (Class<T>) params[0];
        Class<ID> idClass = (Class<ID>) params[1];
        this.op = new EntityOperation<>(entityClass, getPKColumn());
    }

    public Serializable get(Serializable id) {
        return null;
    }

    // protected abstract List<T> getAll();
    //
    // protected abstract byte[] getBlobColumn(ResultSet rs, int columnIndex);
    // protected abstract String getClobColumn(ResultSet rs, int columnIndex);

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
        // 装饰器模式
        return this.jdbcTemplateReadOnly().queryForList(sql, param);
    }

    protected String getTableName() {
        return this.op.tableName;
    }

    protected String remoteFirstAnd(String whereSql) {
        return null;
    }
    //
    // protected abstract boolean delete(Serializable entity);
    // protected abstract int deleteAll(List<?> list);
    // protected abstract void deleteByPK(Serializable id);
    //
    // protected abstract boolean exists(Serializable id);

    /**
     * 获取主键的列名
     * @return
     */
    protected abstract String getPKColumn();
}
