package cn.gyw.handwritten.gspring.beans.factory.config;

/**
 * Bean 定义
 * 
 * Spring 中使用接口进行解耦不同的ApplicationContext
 */
public class GBeanDefinition {

	// 类名
	private String beanClassName;
	// 是否懒加载
	private boolean lazyInit;
	// 对象工厂
	private String factoryBeanName;
	// 是否但李
	private boolean singleton = true;

	public String getBeanClassName() {
		return beanClassName;
	}

	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
	}

	public boolean isLazyInit() {
		return lazyInit;
	}

	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}

	public String getFactoryBeanName() {
		return factoryBeanName;
	}

	public void setFactoryBeanName(String factoryBeanName) {
		this.factoryBeanName = factoryBeanName;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}
}
