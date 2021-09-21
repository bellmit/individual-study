package cn.gyw.handwritten.gspring.aop.adapter;

import cn.gyw.handwritten.gspring.aop.GAdvice;

/**
 * JoinPoint 表示方法的调用过程
 * 内部包含了方法调用过程中的所有信息，
 * 比如被调用的方法、目标、代理对象、执行拦截器链等信息
 */
public interface GJoinPoint extends GAdvice {

	/**
	 * 转到拦截器链中的下一个拦截器
	 * @return
	 * @throws Throwable
	 */
    Object proceed() throws Throwable;

    /**
     * 返回保存当前连接点静态部分【对象】，这里一般指被代理的目标对象
     * @return
     */
    Object getThis();

    Object[] getArguments();
    
    // 自定义参数
    void setExt(String key, Object value);
    
    Object getExt(String key);
}
