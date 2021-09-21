package cn.gyw.handwritten.gspring.beans.factory;

import cn.gyw.handwritten.gspring.beans.GBeanFactory;

/**
 * 扩展顶层接口{@link GBeanFactory}，用于枚举工厂中的所有bean实例，而不是尝试bean查找
 */
public interface GListableBeanFactory extends GBeanFactory {
	
	/**
	 * Return the number of beans defined in the factory.
	 * @return the number of beans defined in the factory
	 */
	int getBeanDefinitionCount();

	/**
	 * Return the names of all beans defined in this factory.
	 * @return the names of all beans defined in this factory,
	 * or an empty array if none defined
	 */
	String[] getBeanDefinitionNames();

}
