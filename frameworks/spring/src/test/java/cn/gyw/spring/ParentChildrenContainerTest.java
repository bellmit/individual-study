package cn.gyw.spring;

import cn.gyw.spring.config.UserServiceConfig1;
import cn.gyw.spring.config.UserServiceConfig2;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 父子容器测试
 */
public class ParentChildrenContainerTest {

    /**
     * ApplicationContext
     *
     * 父子容器解决同名bean 冲突
     */
    @Test
    public void test3() {
        // 父容器
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        parentContext.register(UserServiceConfig1.class);
        // 启动容器
        parentContext.refresh();

        // 子容器
        AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
        childContext.register(UserServiceConfig2.class);
        // 设置父容器
        childContext.setParent(parentContext);
        // 启动容器
        childContext.refresh();

        Object obj = childContext.getBean("userService");
        System.out.println(obj.getClass().getName());
    }

    /**
     * beanFactory
     *
     * 父子容器解决同名bean 冲突
     */
    @Test
    public void test2() {
        // 创建父容器
        DefaultListableBeanFactory parentFactory = new DefaultListableBeanFactory();
        // 创建子容器
        DefaultListableBeanFactory childFactory = new DefaultListableBeanFactory();

        // 调用setParentBeanFactory指定父容器
        childFactory.setParentBeanFactory(parentFactory);
    }

    /**
     * 单一容器会有bean name 冲突
     */
    @Test
    public void test1() {
        // 定义容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 注册bean
        context.register(UserServiceConfig1.class, UserServiceConfig2.class);

        // 启动容器
        context.refresh();
    }

}
