package cn.gyw.handwritten.gspring.beans;

/**
 * 单例工厂的顶层实现
 */
public interface GBeanFactory {

    /**
     * 根据beanName 从IOC容器中获取一个实例bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName) throws Exception;

    Object getBean(Class<?> beanClass) throws Exception;
}
