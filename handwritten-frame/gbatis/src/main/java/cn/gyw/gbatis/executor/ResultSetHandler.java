package cn.gyw.gbatis.executor;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 结果集处理器
 */
public class ResultSetHandler {

    public <T> List<T> handle(ResultSet resultSet, Class<?> type) {
        List<T> resultList = new ArrayList<>();
        try {
            T result = (T) type.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int colCount = metaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 0; i < colCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Field field = type.getDeclaredField(columnName);
                    field.set(result, resultSet.getObject(columnName));
                }
                resultList.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
