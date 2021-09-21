package cn.gyw.gbatis.executor;

import cn.gyw.gbatis.config.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * 执行器
 * ：SqlSession 的功能都是由Executor 执行器来执行
 *
 */
public interface Executor {

    /**
     * 查询接口
     * @param mappedStatement 封装sql语句的MappedStatement对象
     * @param parameters 传入sql 的参数
     * @param <E>
     * @return 将数据转换成指定对象结果集返回
     * @throws SQLException
     */
    <E> List<E> query(MappedStatement mappedStatement, Object... parameters) throws SQLException;

}
