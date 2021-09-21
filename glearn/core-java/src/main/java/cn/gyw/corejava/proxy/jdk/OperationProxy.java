package cn.gyw.corejava.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Operation 代理类
 *
 * 拦截代理对象的所有方法，包括 ： hashCode、equals 方法等；
 * Created by guanyw on 2019/3/4.
 */
public class OperationProxy<T> implements InvocationHandler {

    private T target;

    public OperationProxy(T target) {
        this.target = target;
    }

    /**
     *
     * @param proxy 动态代理对象
     * @param method 正在执行的方法
     * @param args 调用目标方法时传入的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---开始执行方法：" + method.getName());
       Object result = method.invoke(target, args);
       System.out.println("---" + method.getName() + " 方法执行完成");
        return result;
    }
}
