package cn.gyw.corejava.junit;

import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * 委托执行对象
 */
public class JunitCustomStatement extends Statement {

    private final Statement invoker;

    // 拦截器对象集合
    private List<JunitIntercepter> intercepters = new ArrayList<>();

    @Override
    public void evaluate() throws Throwable {
        intercepters.stream().forEach((ele) -> {
            ele.interceptBefore();
        });
        invoker.evaluate();
        intercepters.stream().forEach((ele) -> {
            ele.interceptAfter();
        });
    }

    public JunitCustomStatement(Statement statement) {
        this.invoker = statement;
    }

    /**
     * 添加拦截器
     * @param intercepter
     */
    public void addIntercepter(JunitIntercepter intercepter) {
        intercepters.add(intercepter);
    }
}
