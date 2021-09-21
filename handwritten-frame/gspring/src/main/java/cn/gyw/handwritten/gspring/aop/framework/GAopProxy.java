package cn.gyw.handwritten.gspring.aop.framework;

/**
 * 代理类的顶层设计
 */
public interface GAopProxy {

	Object getProxy();

	Object getProxy(ClassLoader classLoader);
}
