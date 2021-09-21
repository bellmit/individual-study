package cn.gyw.mybatis.plugins;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.Properties;

/**
 * 拦截器bind 四大对象
 *
 * 自定义插件的实现：物理翻页，sql语句及执行时间输出
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "parameterize", args = Statement.class)
})
public class PageHelperPlugin implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(PageHelperPlugin.class);

    /**
     * 拦截目标对象的目标方法的执行；
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info("target :{}, method :{}", invocation.getTarget(), invocation.getMethod());
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 执行原来的方法
        Object result = invocation.proceed();

        return result;
    }

    /**
     * 包装目标对象
     *  包装：为目标对象创建一个代理对象
     * @param o 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object o) {
        logger.debug("Wrap target object, return proxy object");
        // 根据是否要拦截，返回目标对象或代理对象
        Object objWrapper = Plugin.wrap(o, this);
        return objWrapper;
    }

    /**
     * 注册时的property 属性
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        logger.info("plugin property :{}", properties);
    }
}
