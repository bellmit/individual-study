package cn.gyw.platform.gorm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体操作封装
 * <p>
 * 结果集处理
 */
public class EntityOperation<T> {

    private static final Logger log = LoggerFactory.getLogger(EntityOperation.class);

    // 泛型实体Class 对象
    public Class<T> entityClass;
    public final Map<String, PropertyMapping> mappings;
    public final RowMapper<T> rowMapper;

    public final String tableName;
    public String allColumn = "*";
    public Field pkField;

    public EntityOperation(Class<T> clazz, String pk) throws Exception {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new Exception(clazz.getName() + " 中没有Entity注解，无法ORM映射");
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

    private Map<String, PropertyMapping> getPropertyMappings(Map<String, Method> getters, Map<String, Method> setters,
                                                             Field[] fields) {
        Map<String, PropertyMapping> propMappingMap = new HashMap<>();
        String fieldName;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            fieldName = field.getName();
            if (fieldName.startsWith("is")) {
                fieldName = fieldName.substring(2);
            }
            fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
            if (!getters.containsKey(fieldName) || !setters.containsKey(fieldName)) {
                continue;
            }
            propMappingMap.put(fieldName,
                    new PropertyMapping(getters.get(fieldName), setters.get(fieldName), field));
        }
        return propMappingMap;
    }

    private void fillPkFieldAndAllColumn(String pk, Field[] fields) {
        for (Field field : fields) {
            if (pk.equals(field.getName())) {
                pkField = field;
                return;
            }
        }
    }

    private RowMapper<T> createRowMapper() {
        return (rs, rowNum) -> {
            try {
                T t = entityClass.newInstance();
                ResultSetMetaData meta = rs.getMetaData();
                int columns = meta.getColumnCount();
                String columnName;
                for (int i = 1; i < columns; i++) {
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

    void fillBeanFieldValue(T entity, String columnName, Object value)
            throws InvocationTargetException, IllegalAccessException {
        if (!this.mappings.containsKey(columnName)) {
            log.warn("[{}] has no mapping!", columnName);
            return;
        }
        PropertyMapping propMapping = this.mappings.get(columnName);
        Method setter = propMapping.getSetter();
        setter.invoke(entity, value);
    }

    public static class PropertyMapping {
        private Method getter;
        private Method setter;
        private Field field;

        public PropertyMapping() {
        }

        public PropertyMapping(Method getter, Method setter, Field field) {
            this.getter = getter;
            this.setter = setter;
            this.field = field;
        }

        public Method getGetter() {
            return getter;
        }

        public void setGetter(Method getter) {
            this.getter = getter;
        }

        public Method getSetter() {
            return setter;
        }

        public void setSetter(Method setter) {
            this.setter = setter;
        }
    }
}
