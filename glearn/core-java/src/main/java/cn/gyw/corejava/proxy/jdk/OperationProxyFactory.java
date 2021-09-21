package cn.gyw.corejava.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * 代理类生成工厂
 *
 * Created by guanyw on 2019/3/4.
 */
public class OperationProxyFactory<T> {

    private Class<T> specifyInterface;

    private T target;

    public OperationProxyFactory(T target, Class<T> specifyInterface) {
        this.target = target;
        this.specifyInterface = specifyInterface;
    }

    protected T newInstance(OperationProxy<T> operationProxy) {
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(), new Class[]{specifyInterface}, operationProxy);
    }

    public T newInstance() {
        OperationProxy<T> operationProxy = new OperationProxy<>(target);
        return this.newInstance(operationProxy);
    }

}
