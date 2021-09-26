package cn.gyw.platform.gorm;

import cn.gyw.platform.gorm.entity.Person;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTest {

    /**
     * 通用处理逻辑
     */
    @Test
    public void commonSelect() {
        Person condition = new Person();
        condition.setAge(21);

        List<?> data = select(condition);
        System.out.println(data);
    }

    private List<?> select(Object condition) {
        List<Object> list = new ArrayList<>();
        Class<?> entityClass = condition.getClass();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Field[] fields = entityClass.getDeclaredFields();
        // columnName -> fieldName
        Map<String, String> mapper = new HashMap<>();
        Map<String, String> getColumnByFieldName = new HashMap<>();

        for (int i = 0, len = fields.length; i < len; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(Column.class)) {
                String columnName = field.getAnnotation(Column.class).name();
                mapper.put(columnName, field.getName());
                getColumnByFieldName.put(field.getName(), columnName);
            } else {
                mapper.put(field.getName(), field.getName());
                getColumnByFieldName.put(field.getName(), field.getName());
            }
        }

        try {
            String url = "jdbc:mysql://192.168.1.181:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";
            // 加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            // 建立连接
            connection = DriverManager.getConnection(url, "root", "root");

            Table table = entityClass.getAnnotation(Table.class);
            // 创建语句集
            String sql = "SELECT * from " + table.name();
            StringBuilder where = new StringBuilder(" where 1=1 ");
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(condition);
                if (value != null) {
                    if (String.class == field.getType()) {
                        where.append(" and " + getColumnByFieldName.get(field.getName()) + " = '" + value + "'");
                    } else {
                        where.append(" and " + getColumnByFieldName.get(field.getName()) + " = " + value);
                    }
                }
            }
            // 执行语句集
            ps = connection.prepareStatement(sql + where.toString());

            // 获取结果集
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {
                Object instance = entityClass.newInstance();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String column = metaData.getColumnName(i);
                    // 获取对应的属性
                    Field field = entityClass.getDeclaredField(mapper.get(column));
                    field.setAccessible(true);
                    field.set(instance, rs.getObject(column));
                }
                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭结果集/语句集/连接
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 手工处理逻辑
     */
    @Test
    public void jdbcSelect() {
        List<Object> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Class<?> entityClass = Person.class;
        try {
            String url = "jdbc:mysql://192.168.1.181:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";
            // 加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            // 建立连接
            connection = DriverManager.getConnection(url, "root", "root");

            Table table = entityClass.getAnnotation(Table.class);
            // 创建语句集
            String sql = "SELECT * from " + table.name();
            // 执行语句集
            ps = connection.prepareStatement(sql);

            // 获取结果集
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();

            while (rs.next()) {
                // 方式1：纯手工
                Person m = packageMember(rs);
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭结果集/语句集/连接
            try {
                rs.close();
                ps.close();
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println(list);
    }

    /**
     * 简单获取属性并创建对象
     *
     * @param row
     * @return
     */
    private Person packageMember(ResultSet row) throws SQLException {
        Person m = new Person();
        m.setId(row.getInt("id"));
        m.setUsername(row.getString("name"));
        m.setEmail(row.getString("addr"));
        m.setAge(row.getInt("age"));
        return m;
    }
}
