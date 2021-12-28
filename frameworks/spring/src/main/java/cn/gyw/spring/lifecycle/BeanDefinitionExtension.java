package cn.gyw.spring.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @desc BeanDefinition 扩展点
 * <p>
 * 1. BeanDefinitionRegistryPostProcessor方式:BeanDefinitionRegistryPostProcessor是BeanDefinition注册后置处理器，
 * 它本身是BeanFactoryPostProcessor的扩展，允许在BeanFactoryPostProcessor处理前向上下文中注册更多的BeanDefinition
 * <p>
 * 2. BeanFactoryPostProcessor方式:BeanFactoryPostProcessor是容器的扩展点，用于更进一步处理上下文中的BeanDefinition
 * <p>
 * 3. ImportBeanDefinitionRegistrar方式:ImportBeanDefinitionRegistrar也是BeanDefinition注册器，用于向上下文注册更多的BeanDefinition。
 * 不过它是被应用在注解处理BeanDefinition的场景中，即自定义注解，然后利用ImportBeanDefinitionRegistrar其实现向上下文中注册自定义注解标注的Bean定义。
 * <p>
 * 4. BeanDefinitionParser方式:BeanDefinitionParser是BeanDefinition解析器，它是Spring提供为扩展解析XML配置的Bean而设计。
 * 它不仅能够解析XML向上下文中注册更多BeanDefiniion，同时还支持自定义XML Tag
 * @createdTime 2021/12/23 22:06
 */
public class BeanDefinitionExtension implements BeanDefinitionRegistryPostProcessor, BeanFactoryPostProcessor {

    /**
     * BeanFactoryPostProcessor处理前向上下文中注册更多的BeanDefinition
     *
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println(">> BeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println(">> BeanDefinitionRegistryPostProcessor.postProcessBeanFactory");
    }
}
