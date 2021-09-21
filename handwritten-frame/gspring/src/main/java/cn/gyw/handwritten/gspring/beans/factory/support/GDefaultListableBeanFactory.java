package cn.gyw.handwritten.gspring.beans.factory.support;

import cn.gyw.handwritten.gspring.beans.factory.config.GBeanDefinition;
import cn.gyw.handwritten.gspring.context.support.GAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认 BeanFactory
 */
public class GDefaultListableBeanFactory extends GAbstractApplicationContext {

    // 注册信息BeanDefinition
    protected final Map<String, GBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
}
