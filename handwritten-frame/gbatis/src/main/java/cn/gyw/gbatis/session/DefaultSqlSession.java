package cn.gyw.gbatis.session;

import java.sql.SQLException;
import java.util.List;

import cn.gyw.gbatis.config.Configuration;
import cn.gyw.gbatis.config.MappedStatement;
import cn.gyw.gbatis.executor.SimpleExecutor;
import cn.gyw.gbatis.executor.Executor;

public class DefaultSqlSession implements SqlSession {

    // 配置对象，全局唯一
    private final Configuration configuration;

    // 底层依赖的executor 对象
    private final Executor executor;

    // 是否自动提交事务
    private final boolean autoCommit;

    public DefaultSqlSession(Configuration configuration, Executor executor, boolean autoCommit) {
        this.configuration = configuration;
        this.executor = executor;
        this.autoCommit = autoCommit;
    }

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this(configuration, executor, false);
    }

    public DefaultSqlSession(Configuration configuration) {
        this(configuration, new SimpleExecutor(configuration), false);
    }

    @Override
    public <T> T selectOne(String statementId) {
        return this.<T>selectOne(statementId, null);
    }

    @Override
    public <T> T selectOne(String statementId, Object parameter) {
        List<T> list = this.<T>selectList(statementId, parameter);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    public <E> List<E> selectList(String statementId) {
        return this.<E>selectList(statementId, null);
    }

    @Override
    public <E> List<E> selectList(String statementId, Object parameter) {
        MappedStatement ms = this.configuration.getMappedStatement(statementId);
        try {
            return this.executor.query(ms, parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T getMapper(Class<T> mapperClazz) {
        return this.configuration.getMapper(mapperClazz, this);
    }
}
