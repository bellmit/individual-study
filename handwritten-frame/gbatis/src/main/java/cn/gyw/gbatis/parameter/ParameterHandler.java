package cn.gyw.gbatis.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 参数处理器
 */
public class ParameterHandler {

    private PreparedStatement ps;

    public ParameterHandler(PreparedStatement preparedStatement) {
        this.ps = preparedStatement;
    }

    /**
     * 设置参数
     *
     * @param parameters
     */
    public void setParameters(Object[] parameters) {
        try {
            for (int i = 0, len = parameters.length; i < len; i++) {
                Object param = parameters[i + 1];
                if (param instanceof Integer) {
                    ps.setInt(i, (Integer) param);
                } else if (param instanceof Long) {
                    ps.setLong(i, (Long) param);
                } else if (param instanceof String) {
                    ps.setString(i, (String) param);
                } else if (param instanceof Boolean) {
                    ps.setBoolean(i, (Boolean) param);
                } else {
                    ps.setString(i, (String) param);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
