package cn.gyw.gbatis.session;

import cn.gyw.gbatis.config.Configuration;

/**
 * 生产 SqlSession
 */
public class SqlSessionFactory {

    // final 全局唯一
    private final Configuration configuration;

    // 初始化配置
    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }

}
