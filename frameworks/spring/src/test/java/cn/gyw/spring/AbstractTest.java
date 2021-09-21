package cn.gyw.spring;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class AbstractTest {

    protected ApplicationContext apc;

    private Class<?> configClass;

    AbstractTest() {}

    protected AbstractTest(Class configClass) {
        this.configClass = configClass;
    }

    @Before
    public void before() {
        apc = new AnnotationConfigApplicationContext(configClass);
        System.out.println(AbstractTest.class.getSimpleName() + " > IOC 容器初始化完成...");
    }
}
