package cn.gyw.handwritten.gspring.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class GJdkDynamicAopProxy implements GAopProxy, InvocationHandler {

    /**
     * Config used to configure this proxy.
     */
    private final GAdvisedSupport advised;

    public GJdkDynamicAopProxy(GAdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, this.advised.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers =
				this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());
        GReflectiveMethodInvocation invocation = new GReflectiveMethodInvocation(proxy,
                this.advised.getTarget(), method, args,
                this.advised.getTargetClass(), interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }

}
