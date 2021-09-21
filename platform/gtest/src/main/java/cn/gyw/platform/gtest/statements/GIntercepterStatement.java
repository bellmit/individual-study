package cn.gyw.platform.gtest.statements;

import org.junit.runners.model.Statement;

import cn.gyw.platform.gtest.intercepters.GIntercepter;

import java.util.ArrayList;
import java.util.List;

/**
 * junit 委托执行的对象
 */
public class GIntercepterStatement extends Statement {

    private final Statement invoker;

    // 拦截器对象集合
    private List<GIntercepter> intercepters = new ArrayList<>();

    @Override
    public void evaluate() throws Throwable {
        intercepters.stream().forEach((ele) -> {
            ele.doBefore();
        });
        invoker.evaluate();
        intercepters.stream().forEach((ele) -> {
            ele.doAfter();
        });
    }

    public GIntercepterStatement(Statement statement) {
        this.invoker = statement;
    }

    /**
     * 添加拦截器
     * @param interceptor
     */
    public void addIntercepter(GIntercepter interceptor) {
        intercepters.add(interceptor);
    }
}
