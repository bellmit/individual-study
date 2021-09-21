package cn.gyw.components.web.log.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.gyw.components.web.log.entity.ApiLog;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import cn.gyw.components.web.base.mybatisplus.IBaseDao;

/**
 * api 调用日志 dao
 */
public class ApiLogShardingDao implements IBaseDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApiLogShardingDao.class);

    @Autowired
    @Qualifier("apiLogJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public int selectCount() {
        return 0;
    }

    @Override
    public boolean insert(String table, ApiLog apiLog) {
        List<Triple<String, Integer, Object>> insertFields = Collections.emptyList();
        try {
            insertFields = setInsertParams(apiLog);
        } catch (Exception e) {
            // 日志异常处理
            LOGGER.error("{}", e);
        }
        StringBuilder sqlBuilder = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        sqlBuilder.append("INSERT INTO ").append(table).append(" (");
        boolean first = true;
        for (Triple<String, Integer, Object> param : insertFields) {
            if (first) {
                first = false;
            } else {
                sqlBuilder.append(", ");
                placeholders.append(", ");
            }
            sqlBuilder.append(param.getLeft());
            placeholders.append("?");
        }
        sqlBuilder.append(") VALUES (").append(placeholders.toString()).append(")");
        LOGGER.debug("insert statement is:[{}]", sqlBuilder.toString());
        int ret = jdbcTemplate.update(sqlBuilder.toString(), createStatementSetter(insertFields));
        return ret == 1;
    }

    @Override
    public boolean update(String table, ApiLog apiLog) {
        return false;
    }

    private List<Triple<String, Integer, Object>> setInsertParams(ApiLog apiLog) throws Exception {
        List<Triple<String, Integer, Object>> insertFields = new ArrayList<>();
        Class<?> clazz = apiLog.getClass();
        Field[] apiLogFields = clazz.getDeclaredFields();
        PropertyDescriptor pd = null;
        String fieldName = null;
        Object fieldValue = null;
        for (Field apiLogField : apiLogFields) {
            fieldName = apiLogField.getName();
            if (fieldName.equals("serialVersionUID")) {
                continue;
            }
            pd = new PropertyDescriptor(fieldName, clazz);
            fieldValue = pd.getReadMethod().invoke(apiLog);
            if (Objects.isNull(fieldValue)) {
                continue;
            }
            if (apiLogField.getType() == Date.class) {
                Date dt = (Date) fieldValue;
                addDateField(insertFields, fieldName, Timestamp.from(dt.toInstant()));
                break;
            }
            addStringField(insertFields, fieldName, String.valueOf(fieldValue));
        }
        LOGGER.debug("insert fields is :[{}]", insertFields);
        return insertFields;
    }

    private void addStringField(List<Triple<String, Integer, Object>> list, String col, String value) {
        list.add(new ImmutableTriple<String, Integer, Object>(col, Types.VARCHAR, value));
    }

    private void addDateField(List<Triple<String, Integer, Object>> list, String col, Timestamp value) {
        list.add(new ImmutableTriple<String, Integer, Object>(col, Types.TIMESTAMP, value));
    }

    private PreparedStatementSetter createStatementSetter(List<Triple<String, Integer, Object>> insertFields) {
        PreparedStatementSetter setter = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int idx = 1;
                for (Triple<String, Integer, Object> col : insertFields) {
                    int type = col.getMiddle();
                    Object value = col.getRight();
                    if (Objects.nonNull(value)) {
                        switch (type) {
                            case Types.VARCHAR:
                                ps.setString(idx, (String) value);
                                break;
                            case Types.INTEGER:
                                ps.setInt(idx, (int) value);
                                break;
                            case Types.DATE:
                            case Types.TIMESTAMP:
                                ps.setTimestamp(idx, (Timestamp) value);
                                break;
                            default:
                                ps.setNull(idx, type);
                                LOGGER.error("unknown value type {} for column: {}, set to NULL", type, col.getLeft());
                        }
                    } else {
                        ps.setNull(idx, type);
                    }
                    idx++;
                }
            }
        };
        return setter;
    }
}
