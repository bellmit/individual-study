package cn.gyw.spring;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cn.gyw.spring.lifecycle.LifecycleConfig;
import cn.gyw.spring.model.UserSpring;

/**
 * Bean 生命周期测试
 */
public class BeanLifeCycleTest extends AbstractTest {

    public BeanLifeCycleTest() {
        super(LifecycleConfig.class);
    }

    @Test
    public void testInitMethod() {
        apc.getBean(UserSpring.class);

        ((AnnotationConfigApplicationContext) apc).close();
    }
}
