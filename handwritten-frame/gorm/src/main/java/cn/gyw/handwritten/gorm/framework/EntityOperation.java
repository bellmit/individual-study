package cn.gyw.handwritten.gorm.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSetMetaData;
import java.util.Map;

/**
 * 实体操作封装
 *
 * 结果集处理
 */
public class EntityOperation<T> {

    private Logger log = LoggerFactory.getLogger(EntityOperation.class);

    // 泛型实体Class 对象
    public Class<T> entityClass = null;
    public final Map<String, PropertyMapping> mappings;
    public final RowMapper<T> rowMapper;

    public final String tableName;
    public String allColumn = "*";
    public Field pkField;

    public EntityOperation (Class<T> clazz, String pk) {
        if (clazz.isAnnotationPresent(Entity.class)) {

        }
        this.entityClass = clazz;
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null) {
            this.tableName = table.name();
        } else {
            this.tableName = entityClass.getSimpleName();
        }
        Map<String, Method> getters = ClassMappings.findPublicGetters(entityClass);
        Map<String, Method> setters = ClassMappings.findPublicSetters(entityClass);
        Field[] fields = ClassMappings.findFields(entityClass);
        fillPkFieldAndAllColumn(pk, fields);
        this.mappings = getPropertyMappings(getters, setters, fields);
        this.allColumn = this.mappings.keySet().toString().replace("[", "")
                .replace("]", "");
        this.rowMapper = createRowMapper();
    }

    private Map<String, PropertyMapping> getPropertyMappings(Map<String, Method> getters, Map<String, Method> setters, Field[] fields) {
        return null;
    }

    private void fillPkFieldAndAllColumn(String pk, Field[] fields) {
    }

    private RowMapper<T> createRowMapper() {
        return (rs, rowNum) -> {
            try {
                T t = entityClass.newInstance();
                ResultSetMetaData meta = rs.getMetaData();
                int columns = meta.getColumnCount();
                String columnName;
                for (int i = 1 ; i < columns ;i ++) {
                    Object value = rs.getObject(i);
                    columnName = meta.getColumnName(i);
                    fillBeanFieldValue(t, columnName, value);
                }
                // TODO
                return t;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    void fillBeanFieldValue(T entity, String columnName, Object value) {

    }

    private class PropertyMapping {
    }
}
