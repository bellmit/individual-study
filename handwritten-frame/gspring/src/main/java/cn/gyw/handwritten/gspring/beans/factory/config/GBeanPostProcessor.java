package cn.gyw.handwritten.gspring.beans.factory.config;

/**
 * 通知
 */
public class GBeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
