package cn.gyw.gbatis.session;

import java.util.List;

/**
 * Mybatis 暴露给外部的接口：实现增删改查的能力
 *
 * 1.对外提供数据访问api
 * 2.对内将请求转发给Executor
 */
public interface SqlSession {

    <T> T selectOne(String statement);

    /**
     * 调用 Executor 获取一条数据
     * @param statementId
     * @param parameter
     * @param <T>
     * @return
     */
    <T> T selectOne(String statementId, Object parameter);

    <E> List<E> selectList(String statement);

    <E> List<E> selectList(String statement, Object parameter);

    /**
     * 获取 Mapper的代理对象
     * @param mapperClazz
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> mapperClazz);
}
