package cn.gyw.corejava.proxy;

import cn.gyw.corejava.proxy.jdk.Operation;
import cn.gyw.corejava.proxy.jdk.OperationProxyFactory;
import cn.gyw.corejava.proxy.jdk.Programmer;
import org.junit.Test;

/**
 * Created by guanyw on 2019/3/4.
 */
public class OperationProxyFactoryTest {

    @Test
    public void test() {
        // 被代理类
        Programmer programmer = new Programmer();
        System.out.println("被代理类：" + programmer);
        OperationProxyFactory<Operation> operationProxyFactory = new OperationProxyFactory(programmer, Operation.class);

        Operation programmerAdvice = operationProxyFactory.newInstance();
        System.out.println("代理类：" + programmerAdvice);
        programmerAdvice.execute();
        System.out.println(programmerAdvice.description());
        programmerAdvice.hashCode();
    }

}
