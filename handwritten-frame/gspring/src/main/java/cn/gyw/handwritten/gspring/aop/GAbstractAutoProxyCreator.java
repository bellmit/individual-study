package cn.gyw.handwritten.gspring.aop;

public abstract class GAbstractAutoProxyCreator {

	/**
	 * 使用配置的拦截器创建代理
	 * @param bean
	 * @param beanName
	 * @return
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		// wrapIfNecessary
		return bean;
	}
	
	protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
		// createProxy
		return null;
	}
	
	protected abstract Object createProxy(Class<?> beanClass, String beanName,
										  Object[] specificInterceptors, GTargetSource targetSource);
}
