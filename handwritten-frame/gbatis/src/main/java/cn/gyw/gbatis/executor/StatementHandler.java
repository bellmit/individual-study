package cn.gyw.gbatis.executor;

import cn.gyw.gbatis.config.Configuration;
import cn.gyw.gbatis.config.MappedStatement;
import cn.gyw.gbatis.parameter.ParameterHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 语句集处理器
 */
public class StatementHandler {

    private ResultSetHandler resultSetHandler;
    private Configuration configuration;

    public StatementHandler(Configuration configuration) {
        this.resultSetHandler = new ResultSetHandler();
        this.configuration = configuration;
    }

    // 语句集查询
    public <E> List<E> query(MappedStatement mappedStatement, Object... parameters) {
        return this.doQuery(mappedStatement, parameters);
    }

    private <T> List<T> doQuery(MappedStatement mappedStatement, Object[] parameters) {
        List<T> resultList = new ArrayList<>();
        Connection connection = null;
        try {
            // 驱动加载
            Class.forName(configuration.getDriverClass());
            connection = DriverManager.getConnection(configuration.getDriverUrl(),
                    configuration.getUsername(), configuration.getPassword());

            PreparedStatement ps = connection.prepareStatement(mappedStatement.getSql());
            // 参数处理
            new ParameterHandler(ps).setParameters(parameters);
            ResultSet resultSet = ps.executeQuery();

            // 结果集处理
            Class<?> resultType = Class.forName(mappedStatement.getResultType());
            ResultSetHandler resultSetHandler = new ResultSetHandler();
            resultList = resultSetHandler.handle(resultSet, resultType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }
}
